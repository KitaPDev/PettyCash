package com.kita.pettycash.server.services;

import com.kita.lib.rpc.server.DatabaseAdapter;

import java.sql.*;

public class User {

    private final String DB_URL = "jdbc:postgresql://bridsys.com:57206/pettycash";
    private final String USER = "ise_dev";
    private final String PASSWORD = "icepwd12";

    public boolean AuthenticateUser(String p_strUsername, String p_strPassword) throws SQLException {
        boolean isValidUser = false;
        ResultSet rs = getUserData();

        while(rs.next()) {
            String strUsername = rs.getString("username");
            String strPassword = rs.getString("password");

            if(p_strUsername.equals(strUsername) && p_strPassword.equals(strPassword)) {
                isValidUser = true;
                break;
            }
        }

        return isValidUser;
    }

    public boolean isExistingUser(String p_strUsername) throws SQLException {
        boolean isExistingUser = false;
        ResultSet rs = getUserData();

        while(rs.next()) {
            String strUsername = rs.getString("username");

            if(p_strUsername.equals(strUsername)) {
                isExistingUser = true;
                break;
            }
        }

        return isExistingUser;
    }

    public ResultSet getUserData() {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);
        String getSql = "SELECT username, password FROM users;";
        ResultSet rs = databaseAdapter.getResultSet(conn, getSql);

        return rs;
    }

    public void CreateUser(String p_strUsername, String p_strPassword) {
        Connection conn;
        Statement stmt;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            stmt = conn.createStatement();

            String insertSql = "INSERT INTO users (username, password) VALUES (" + p_strUsername + ", " + p_strPassword + ");";

            stmt.executeUpdate(insertSql);

        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void ChangePassword(String p_strUsername, String p_strPassword) {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter();

        String insertSql = "UPDATE users SET password = \'" + p_strPassword + "\' WHERE username = \'" + p_strUsername + "\';";

        Connection conn = databaseAdapter.establishConnection(DB_URL, USER, PASSWORD);

        databaseAdapter.update(conn, insertSql);
    }
}
