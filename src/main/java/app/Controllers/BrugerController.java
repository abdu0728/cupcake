package app.Controllers;

import app.entities.Bruger;
import app.persistence.BrugerDao;
import io.javalin.http.Context;

public class BrugerController {

    private final BrugerDao brugerDao;

    public BrugerController(BrugerDao brugerDao) {
        this.brugerDao = brugerDao;
    }

    public void showLoginPage(Context ctx) {
        ctx.render("login.html");
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String kodeord = ctx.formParam("kodeord");

        Bruger bruger = brugerDao.findByEmail(email);
        if (bruger != null && bruger.getKodeord().equals(kodeord)) {
            ctx.sessionAttribute("currentUser", bruger);
            ctx.redirect("/index.html");
        } else {
            ctx.attribute("error", "Forkert email eller kodeord");
            ctx.render("login.html");
        }
    }

    public void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }

    public void showSignupPage(Context ctx) {
        ctx.render("signup.html");
    }

    public void signup(Context ctx) {
        String navn = ctx.formParam("navn");
        String email = ctx.formParam("email");
        String kodeord = ctx.formParam("kodeord");

        Bruger bruger = new Bruger();
        bruger.setBruger_id(bruger);
        bruger.setEmail(email);
        bruger.setKodeord(kodeord);
        bruger.setSaldo(100); // start-saldo
        bruger.setRolle("user");

        brugerDao.create(bruger);
        ctx.redirect("/login");
    }
}

