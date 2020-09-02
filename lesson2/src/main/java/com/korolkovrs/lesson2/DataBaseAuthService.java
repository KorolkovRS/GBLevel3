package com.korolkovrs.lesson2;


import java.sql.*;

public class DataBaseAuthService implements AuthService {
    private Connection connection;

    public DataBaseAuthService() {
        connection = DataBaseConnectionService.getConnection();
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

    @Override
    public boolean changeNickname(int id, String newNick) {
        String sql = String.format("UPDATE userlist SET name = '%s' WHERE id = %d", newNick, id);
        try {
            Statement updateStatement = connection.createStatement();
            int result = updateStatement.executeUpdate(sql);
            System.out.println(result);
            return (result != 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

