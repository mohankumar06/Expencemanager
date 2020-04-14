package com.springboot.expencemanager.dto;

public class ExpenseUIDto {

    private String date;
    private long amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}
