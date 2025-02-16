package org.example;
import java.sql.*;

public class Expense
{
    private final String title;
    private final String category;
    private final double amount;
    private final Date date;

    //Constructor
    public Expense(String title, String category, double amount, Date date)
    {
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    //Getters
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
