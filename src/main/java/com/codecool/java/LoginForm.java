package com.codecool.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginForm implements HttpHandler {

    private DBloginForm loginformDB;

    public LoginForm() {

        loginformDB = new DBloginForm();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        //Map<String, String> dbpairs = loginformDB.getPairs();
        String response = "";

        if (method.equals("GET")) {

            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/loginForm.twig");
            JtwigModel model = JtwigModel.newModel();
            model.with("pairs", loginformDB.getPairs() );

            response = template.render(model);
        }

        if (method.equals("POST")) {

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);

            Map inputs = parseFormData(formData);

            String login = inputs.get("login").toString();
            String password = inputs.get("pass").toString();


            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/gbook.twig");
            JtwigModel model = JtwigModel.newModel();
            model.with("messageList", loginformDB.getPairs());

            response = template.render(model);
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private boolean isValid(String login, String pass) {

        if (loginformDB.getPairs().get(login).equals(pass)){
            return true;
        } else return false;
    }

}
