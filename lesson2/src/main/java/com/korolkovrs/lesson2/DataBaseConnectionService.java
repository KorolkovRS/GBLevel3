package com.korolkovrs.lesson2;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectionService {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found");
        }
    }
    public static Connection getConnection() {

        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection("jdbc:mysql://localhost:8888/chat", "root", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Driver Registration error");
        }
    }
}
