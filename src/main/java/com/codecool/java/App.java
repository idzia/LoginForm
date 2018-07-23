package com.codecool.java;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


public class App {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/loginform", new LoginForm());
        server.createContext("/profile", new Profile());
        server.createContext("/static", new Static());
        server.createContext("/logout", new Logout());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();

    }
}