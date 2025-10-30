package app.Controllers;


import app.entities.Bund;
import app.entities.Cupcake;
import app.entities.Top;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class KurvController {

    public void addToCart(Context ctx) {
        // Hent topping og bottom fra formularen
        String toppingName = ctx.formParam("topping");
        String bottomName = ctx.formParam("bottom");
        double toppingPrice = Double.parseDouble(ctx.formParam("toppingPrice"));
        double bottomPrice = Double.parseDouble(ctx.formParam("bottomPrice"));

        Top top = new Top(toppingName, toppingPrice) ;
        Bund bund = new Bund(bottomName, bottomPrice) ;
        Cupcake cupcake = new Cupcake(bund, top);

        List<Cupcake> kurv = ctx.sessionAttribute("kurv");
        if (kurv == null) {
            kurv = new ArrayList<>();
        }

        kurv.add(cupcake);

        ctx.sessionAttribute("kurv", kurv);
        ctx.redirect("/index.html");
    }

    public void showCart(Context ctx) {
        List<Cupcake> kurv = ctx.sessionAttribute("cart");
        if (kurv == null) {
            kurv = new ArrayList<>();
        }

        double total = kurv.stream()
                .mapToDouble(Cupcake::getTotalPrice)
                .sum();

        ctx.attribute("kurv", kurv);
        ctx.attribute("total", total);
        ctx.render("kurv.html");
    }

    public void removeFromCart(Context ctx) {
        int index = Integer.parseInt(ctx.pathParam("index"));
        List<Cupcake> kurv = ctx.sessionAttribute("cart");

        if (kurv != null && index >= 0 && index < kurv.size()) {
            kurv.remove(index);
            ctx.sessionAttribute("kurv", kurv);
        }

        ctx.redirect("/cart");
    }

}
