package app.Controllers;

import app.entities.Cupcake;
import app.entities.Order;
import app.entities.Bruger;
import app.persistence.OrderDAO;
import app.persistence.BrugerDao;
import io.javalin.http.Context;
import org.eclipse.jetty.server.Authentication;

import java.time.LocalDate;
import java.util.List;

public class OrderController {

    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


    public void placeOrder(Context ctx) {
        Bruger bruger= ctx.sessionAttribute("currentUser");
        List<Cupcake> cart = ctx.sessionAttribute("cart");

        if (bruger == null) {
            ctx.redirect("/login");
            return;
        }

        if (cart == null || cart.isEmpty()) {
            ctx.attribute("error", "Kurven er tom");
            ctx.redirect("/kurv.html");
            return;
        }

        double totalPrice = cart.stream()
                .mapToDouble(Cupcake::getTotalPrice)
                .sum();

        if (bruger.getSaldo() < totalPrice) {
            ctx.attribute("error", "Du har ikke nok penge på din konto");
            ctx.redirect("/cart");
            return;
        }

        bruger.setSaldo((int) (bruger.getSaldo() - totalPrice));

        Order order = new Order(0, bruger, cart, totalPrice, true, LocalDate.now(),LocalDate.now());

        orderDAO.createOrder(order);

        BrugerDao.updateSaldo(bruger.getBruger_id(),bruger.getSaldo());


    }


    public void showUserOrders(Context ctx) {
        Bruger bruger = ctx.sessionAttribute("currentUser");
        if (bruger == null) {
            ctx.redirect("/login");
            return;
        }

        List<Order> orders = orderDAO.findOrdersByUser(bruger.getBruger_id());
        ctx.attribute("orders", orders);
        ctx.render("orders.html");
    }
}

