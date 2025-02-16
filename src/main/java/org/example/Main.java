package org.example;
import java.sql.*;
import java.util.Scanner;


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
             Statement stmt = conn.createStatement()) {
            String[] mainMenu =
                    {
                            "1. Display Expenses(Total Expenses))",
                            "2. Add Expense",
                            "3. Delete Expense",
                            "4. Display Income",
                            "5. Add Income",
                            "6. Delete Income",
                            "7. Display Monthly Finances",
                            "8. Exit"
                    };
            int mainMenuChoice;
            do {
                {
                    Scanner Keyboard = new Scanner(System.in);
                    System.out.println("\nConnected to the database.");
                    Menu.displayMenu(mainMenu, "Financial Management Application");

                    mainMenuChoice = Menu.getMenuChoice(mainMenu.length);
                    switch (mainMenuChoice)
                    {
                        case 1:
                            //Display Expenses Table
                            ExpenseDAO.displayExpenses(stmt);

                            //Display Calculated Total Amount Spent
                            ExpenseDAO.totalAmount(stmt);
                            break;
                        case 2:
                            //Adding New Row to Expense Table
                            Expense userInput = ExpenseDAO.addInput(Keyboard);
                            ExpenseDAO.addExpense(conn, userInput);

                            //Display Updated Table
                            ExpenseDAO.displayExpenses(stmt);
                            break;
                        case 3:
                            //Deleting a Row From the Expense Table
                            System.out.println("Please enter an expense ID to be deleted: ");
                            int expense_id = Keyboard.nextInt();
                            ExpenseDAO.deleteExpense(conn, expense_id);

                            //Display Updated Table
                            ExpenseDAO.displayExpenses(stmt);
                            break;
                        case 4:
                            //Display Income Table
                            IncomeDAO.displayIncome(stmt);

                            //Display Calculated Total Amount Spent
                            IncomeDAO.totalAmount(stmt);
                            break;
                        case 5:
                            //Adding New Row to Income Table
                            Income input = IncomeDAO.addInput(Keyboard);
                            IncomeDAO.addIncome(conn, input);

                            //Display Updated Table
                            IncomeDAO.displayIncome(stmt);
                            break;
                        case 6:
                            //Deleting a Row From the Income Table
                            System.out.println("Please enter an income ID to be deleted: ");
                            int income_id = Keyboard.nextInt();
                            IncomeDAO.deleteIncome(conn, income_id);

                            //Display Updated Table
                            IncomeDAO.displayIncome(stmt);
                            break;
                        case 7:
                            //Displaying Monthly Finances
                            System.out.println("Please enter a month(in format: yyyy-mm) to view financial statement: ");
                            String month = Keyboard.nextLine();
                            monthlyFinances(month, conn);
                            break;
                        case 8:
                            System.out.println("\nFinished - Disconnected from database");
                            System.exit(0);
                        default:
                            break;
                    }
                }
            }
            while (true);
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    //Method to execute Monthly Expense for particular month outputting Expenses, Income, and Balance
    public static void monthlyFinances(String selectedDate, Connection conn) throws SQLException
    {
        String sql1 = "SELECT * FROM expenses WHERE DATE_FORMAT(EXPENSE_DATE, '%Y-%m') = ?" ;
        try(PreparedStatement expenseStmt = conn.prepareStatement(sql1))
        {
            expenseStmt.setString(1, selectedDate);
            ResultSet expenseData = expenseStmt.executeQuery();

            double totalExpenses = 0;

            System.out.println("\nThe Expenses for " + selectedDate + " is: ");
            System.out.println("---------------------------------------------");
            while(expenseData.next())
            {
                int expenseID = expenseData.getInt("EXPENSE_ID");
                String title = expenseData.getString("TITLE");
                String category = expenseData.getString("CATEGORY");
                double amount = expenseData.getDouble("AMOUNT");
                Date date = expenseData.getDate("EXPENSE_DATE");

                System.out.print("ID: " + expenseID + " ");
                System.out.print("Title: " + title + " ");
                System.out.print("Category: " + category + " ");
                System.out.print("Amount: " + amount + " ");
                System.out.println("Date: " + date);

                totalExpenses += amount;
            }

            String sql2 = "SELECT * FROM income WHERE DATE_FORMAT(DATE_EARNED, '%Y-%m') = ?" ;
            try(PreparedStatement incomeStmt = conn.prepareStatement(sql2)) {
                expenseStmt.setString(1, selectedDate);
                ResultSet incomeData = expenseStmt.executeQuery();

                double totalIncome = 0;

                System.out.println("\nThe Income for " + selectedDate + " is: ");
                System.out.println("---------------------------------------------");
                while (expenseData.next()) {
                    int incomeID = incomeData.getInt("EXPENSE_ID");
                    String title = incomeData.getString("TITLE");
                    double amount = incomeData.getDouble("AMOUNT");
                    Date date = incomeData.getDate("DATE_EARNED");

                    System.out.print("ID: " + incomeID + " ");
                    System.out.print("Title: " + title + " ");
                    System.out.print("Amount: " + amount + " ");
                    System.out.println("Date: " + date);

                    totalIncome += amount;
                }

                double balance = totalIncome - totalExpenses;
                System.out.println("---------------------------------------------");
                System.out.println("Financial Sumaary for " +selectedDate);
                System.out.println("Total Income: " + totalIncome);
                System.out.println("Total Expenses: " + totalExpenses);
                System.out.println("Balance: " + balance);
            }
        }
    }
}
