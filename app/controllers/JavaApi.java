package controllers;

import play.mvc.*;

public class JavaApi extends Controller {

    public static Result connect(String user) {
        session("connected", user);
        return ok("Welcome!");
    }

    public static Result connected() {
        String user = session("connected");
        if (user != null)
            return ok(user);
        else
            return internalServerError("Oops");
    }

    public static Result disconnect() {
        session().clear();
        return ok("Bye");
    }

    public static Result getFlash(String key) {
        String value = flash(key);
        if (value == null) {
            return internalServerError("Oops");
        }
        return ok(value);
    }

    public static Result setFlash(String key, String value) {
        flash(key, value);
        return ok();
    }

}

