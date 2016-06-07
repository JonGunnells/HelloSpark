package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

//route: particular end point
//GET route returns html (typically the first slash route)
//POST route modify the server i.e. anything that modifies data on a server

public class Main {

    static HashMap<String, User> users = new HashMap<>();
    static ArrayList<User> userList = new ArrayList<>();

    public static void main(String[] args) {
        Spark.init();
        Spark.get(
                "/", //slash route, 1st argument
                (request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    HashMap m = new HashMap();
                    if (username == null) {
                        return new ModelAndView(m, "login.html");

                    }
                    else {
                        m.put("name", username);
                        m.put("users", userList);
                        return new ModelAndView(m, "home.html");
                    }
                },
                new MustacheTemplateEngine()

        );
        Spark.post(
                "/login",
                (request, response) -> {
                    String username = request.queryParams("username");
                    User user = users.get(username);
                    if (user == null) {
                        user = new User(username);
                        users.put(username, user);
                        userList.add(user);
                    }

                    Session session = request.session();
                    session.attribute("username", username);

                    response.redirect("/");
                    return "";

                }
        );
        Spark.post(
                "/logout",
                        (request, response) -> {
                            Session session = request.session();
                            session.invalidate();
                            response.redirect("/");
                            return "";
                             }
        );
    }

}



