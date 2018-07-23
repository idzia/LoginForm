package com.codecool.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
    private Connection connection;


    public UserDAO() {
        //DataBaseConnection instance = DataBaseConnection.getInstance();
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    public User getUserByCredentials(String login, String password) {
        User loggedUser = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM users_loginform WHERE login = ? AND password = ?");
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int UserId = resultSet.getInt("userid");
                String UserLogin = resultSet.getString("login");
                String UserPassword = resultSet.getString("password");
                String UserRole = resultSet.getString("role");

                loggedUser = new User(UserId, UserLogin, UserPassword, UserRole);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return loggedUser;
    }


}

