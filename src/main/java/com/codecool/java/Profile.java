package com.codecool.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;

public class Profile implements HttpHandler {



    public void handle(HttpExchange httpExchange) throws IOException {

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String response;

        HttpCookie cookie = null;
        User loggedUser;

        if (cookieStr != null) {

            List<HttpCookie> cookieList = HttpCookie.parse(cookieStr);
            for (HttpCookie c : cookieList) {
                if ( c.getName().equals("sessionId")) {
                    cookie = c;
                }
            }
        } else httpRedirectTo("/loginform", httpExchange);

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/profile.twig");
        JtwigModel model = JtwigModel.newModel();


        if (cookie != null) {

            loggedUser = Session.data.get(cookie.getValue());
            model.with("role", loggedUser.getRole());



        }

        response = template.render(model);

//        if (method.equals("GET")) {
//
//        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void httpRedirectTo(String dest, HttpExchange httpExchange) throws IOException {
        String hostPort = httpExchange.getRequestHeaders().get("host").get(0);
        httpExchange.getResponseHeaders().set("Location", "http://" + hostPort + dest);
        httpExchange.sendResponseHeaders(301, -1);
    }
}
