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

            //Query to print out table
            String sqlQuery = "SELECT * FROM expenses";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next())
            {

                int expenseID = resultSet.getInt("EXPENSE_ID");
                String title = resultSet.getString("TITLE");
                String category = resultSet.getString("CATEGORY");
                double amount = resultSet.getDouble("AMOUNT");
                Date date = resultSet.getDate("EXPENSE_DATE");

                System.out.print("ID: " + expenseID);
                System.out.print("Title: " + title);
                System.out.print("Category: " + category);
                System.out.print("Amount: " + amount);
                System.out.println("Date: " + date);
            }
            String sqlQuery2 = "SELECT SUM(amount) FROM expenses";

            ResultSet sumResult = statement.executeQuery(sqlQuery2);
            while (sumResult.next())
            {
                double totalAmount = sumResult.getDouble(1);
                System.out.println("Total Amount: " + totalAmount);
            }
            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }
}
