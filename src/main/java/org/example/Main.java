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


        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement stmt = conn.createStatement())
        {
            System.out.println("\nConnected to the database.");

            //Display Expenses Table
            displayExpenses(stmt);

            //Display Calculated Total Amount Spent
            totalAmount(stmt);

            //Adding New Row to Expense Table
            addExpense(conn, "Phone Bill", "Utilities", 20.00, Date.valueOf("2025-01-15") );

            System.out.println("\nFinished - Disconnected from database");
        }
        catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    //Method to Display Table Data
    private void displayExpenses(Statement statement) throws SQLException
    {
       String sql = "SELECT * FROM expenses";
       ResultSet display = statement.executeQuery(sql);

       while(display.next())
       {
           int expenseID = display.getInt("EXPENSE_ID");
           String title = display.getString("TITLE");
           String category = display.getString("CATEGORY");
           double amount = display.getDouble("AMOUNT");
           Date date = display.getDate("EXPENSE_DATE");

           System.out.print("ID: " + expenseID + " ");
           System.out.print("Title: " + title + " ");
           System.out.print("Category: " + category + " ");
           System.out.print("Amount: " + amount + " ");
           System.out.println("Date: " + date);
       }
    }

    //Method to Find Sum of Amount of Expenses
    private void totalAmount(Statement statement) throws SQLException
    {
        String sql = "SELECT SUM(amount) FROM expenses";
        ResultSet sumResult = statement.executeQuery(sql);


        if (sumResult.next())
        {
            double totalAmount = sumResult.getDouble(1);
            System.out.println("Total Amount: " + totalAmount);
        }
    }

    //Method to Add a New Expense
    private void addExpense(Connection conn, String title, String category, double amount, Date date) throws SQLException
    {
        String addQuery = "INSERT INTO expenses (TITLE, CATEGORY, AMOUNT, EXPENSE_DATE) VALUES (?,?,?,?)";

        try(PreparedStatement add = conn.prepareStatement(addQuery))
        {
            add.setString(1, title);
            add.setString(2, category);
            add.setDouble(3, amount);
            add.setDate(4, date);
        }
    }
}
