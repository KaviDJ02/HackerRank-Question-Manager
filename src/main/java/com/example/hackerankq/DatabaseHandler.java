package com.example.hackerankq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    private Connection connection;

    private String url = "";
    private String username = "";
    private String password = "";

    private DatabaseHandler() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e);
        }
    }

    public static DatabaseHandler getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}