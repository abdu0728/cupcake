package app.Controllers;


import app.entities.Bund;
import app.entities.Top;
import app.persistence.BundDAO;
import app.persistence.TopDAO;
import io.javalin.http.Context;

import java.util.List;

public class CupcakeController {

    private final TopDAO toppingDAO;
    private final BundDAO bottomDAO;

    public CupcakeController(TopDAO toppingDAO, BundDAO bottomDAO) {
        this.toppingDAO = toppingDAO;
        this.bottomDAO = bottomDAO;
    }

    public void showCupcakePage(Context ctx) {
        try {

            List<Top> toppe = toppingDAO.findAll();
            List<Bund> bunde = bottomDAO.findAll();

            ctx.attribute("toppings", toppe);
            ctx.attribute("bottoms", bunde);

            // Render cupcakes.html (brug Thymeleaf fx)
            ctx.render("cupcakes.html");

        } catch (Exception e) {
            ctx.status(500);
            ctx.result("Fejl ved hentning af toppings og bunde: " + e.getMessage());
        }
    }
}

