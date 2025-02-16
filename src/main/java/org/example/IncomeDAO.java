package org.example;

import java.sql.*;
import java.util.Scanner;

public class IncomeDAO
{
    //Method to Display Table Data
    public static void displayIncome(Statement statement) throws SQLException
    {
        String sql = "SELECT * FROM income";
        ResultSet display = statement.executeQuery(sql);

        while(display.next())
        {
            int incomeID = display.getInt("INCOME_ID");
            String title = display.getString("TITLE");
            double amount = display.getDouble("AMOUNT");
            Date date = display.getDate("PAY_DATE");

            System.out.print("Title: " + title + " | ");
            System.out.print("Amount: " + amount + " | ");
            System.out.println("Date: " + date);
        }
    }

    //Method to Find Sum of Amount of Income
    public static void totalAmount(Statement statement) throws SQLException
    {
        String sql = "SELECT SUM(amount) FROM income";
        ResultSet sumResult = statement.executeQuery(sql);


        if (sumResult.next())
        {
            double totalAmount = sumResult.getDouble(1);
            System.out.println("Total Amount: " + totalAmount);
        }
    }

    //Ask User to Input Add Details
    //https://stackoverflow.com/questions/28114532/how-to-use-keyboard-input-in-a-method
    public static Income addInput(Scanner Keyboard)
    {
        System.out.println("Please enter your income details\n\n");
        System.out.println("Enter income Title: ");
        String incomeTitle = Keyboard.nextLine();
        System.out.println("Enter income Amount: ");
        double incomeAmount = Keyboard.nextDouble();
        Keyboard.nextLine();
        System.out.println("Enter Pay Date in format: (YYYY-MM-DD) ");
        String payDate = Keyboard.nextLine();

        return new Income(incomeTitle, incomeAmount, Date.valueOf(payDate));
    }

    //Method to Add a New Income
    public static void addIncome(Connection conn, Income income) throws SQLException
    {
        String addQuery = "INSERT INTO income (TITLE, AMOUNT, PAY_DATE) VALUES (?,?,?)";

        try(PreparedStatement add = conn.prepareStatement(addQuery))
        {
            add.setString(1, income.getTitle());
            add.setDouble(2, income.getAmount());
            add.setDate(3, income.getPayday());

            int rowsInserted = add.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Income added successfully!");
            }
        }
    }

    //Method to Delete an Income
    public static void deleteIncome(Connection conn, int id) throws SQLException
    {
        String deleteQuery = "DELETE FROM income WHERE INCOME_ID = ?";

        try(PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery))
        {
            deleteStmt.setInt(1, id);
            int rowsAffected = deleteStmt.executeUpdate();
            if (rowsAffected > 0)
            {
                System.out.println("Income with ID: " + id + " was deleted.");
            }
            else
            {
                System.out.println("Income with ID: " + id + " was not found.");
            }
        }
    }
}


