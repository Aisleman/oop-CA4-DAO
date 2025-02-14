package org.example;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    public void start() {
        System.out.println("MySQL - DAO Assignment");

        String url = "jdbc:mysql://localhost/";
        String dbName = "dao_assignment";
        String userName = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
            System.out.println("\nConnected to the database.");

            Statement statement = conn.createStatement();


            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }
}
