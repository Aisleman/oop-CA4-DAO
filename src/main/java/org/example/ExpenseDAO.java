package org.example;

import java.sql.*;
import java.util.Scanner;

public class ExpenseDAO
{
    //Method to Display Table Data
    public static void displayExpenses(Statement statement) throws SQLException
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
    public static void totalAmount(Statement statement) throws SQLException
    {
        String sql = "SELECT SUM(amount) FROM expenses";
        ResultSet sumResult = statement.executeQuery(sql);


        if (sumResult.next())
        {
            double totalAmount = sumResult.getDouble(1);
            System.out.println("Total Amount: " + totalAmount);
        }
    }

    //Ask User to Input Add Details
    //https://stackoverflow.com/questions/28114532/how-to-use-keyboard-input-in-a-method
    public static Expense addInput(Scanner Keyboard)
    {
        System.out.println("Please enter your expense details\n\n");
        System.out.println("Enter expense Title: ");
        String expenseTitle = Keyboard.nextLine();
        System.out.println("Enter expense Category: ");
        String expenseCategory = Keyboard.nextLine();
        System.out.println("Enter expense Amount: ");
        double expenseAmount = Keyboard.nextDouble();
        Keyboard.nextLine();
        System.out.println("Enter expense Date: ");
        String expenseDate = Keyboard.nextLine();

        return new Expense(expenseTitle, expenseCategory, expenseAmount, Date.valueOf(expenseDate));
    }

    //Method to Add a New Expense
    public static void addExpense(Connection conn, Expense expense) throws SQLException
    {
        String addQuery = "INSERT INTO expenses (TITLE, CATEGORY, AMOUNT, EXPENSE_DATE) VALUES (?,?,?,?)";

        try(PreparedStatement add = conn.prepareStatement(addQuery))
        {
            add.setString(1, expense.getTitle());
            add.setString(2, expense.getCategory());
            add.setDouble(3, expense.getAmount());
            add.setDate(4, expense.getDate());

            int rowsInserted = add.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Expense added successfully!");
            }
        }
    }

    //Method to Delete an Expense
    public static void deleteExpense(Connection conn, int id) throws SQLException
    {
        String deleteQuery = "DELETE FROM expenses WHERE EXPENSE_ID = ?";

        try(PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery))
        {
            deleteStmt.setInt(1, id);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Expense with ID: " + id + " was deleted.");
            }
            else
            {
                System.out.println("Expense with ID: " + id + " was not found.");
            }
        }
    }
}


