package org.example;
import java.sql.*;

public class Income
{
    private String title;
    private double amount;
    private Date payday;

    public Income(String title, double amount, Date payday)
    {
        this.title = title;
        this.amount = amount;
        this.payday = payday;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public Date getPayday() {
        return payday;
    }
}
