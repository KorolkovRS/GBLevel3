package com.korolkovrs.lesson2;

import com.mysql.jdbc.Driver;

import java.sql.*;

public class DataBaseAuthService implements AuthService {
    private Connection connection;

    public DataBaseAuthService() {
        connection();
    }

    private void connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found");
        }

        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8888/chat", "root", "Roma655552");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Driver Registration error");
        }
    }

    @Override
    public Record findRecord(String login, String password) {
        Record user = null;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM userlist WHERE (login = ?) AND (password = ?)");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                user = new Record(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
            }

            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public static void main(String[] args) {
        DataBaseAuthService test = new DataBaseAuthService();

        System.out.println(test.findRecord("user1", "qwerty"));
    }

}

