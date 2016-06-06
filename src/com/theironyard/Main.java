package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

//route: particular end point
//GET route returns html (typically the first slash route)
//POST route modify the server i.e. anything that modifies data on a server

public class Main {

    static User user;

    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/", //slash route, 1st argument
                (request, response) -> {    //2nd argument
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "login.html");

                    }
                    else {
                        m.put("name", user.name);
                        return new ModelAndView(m, "home.html");
                    }
                },
                new MustacheTemplateEngine()

        );
        Spark.post(
                "/login",
                (request, response) -> {
                    String username = request.queryParams("username");
                    user = new User(username);
                    response.redirect("/");
                    return "";

                }
        );
    }

}



