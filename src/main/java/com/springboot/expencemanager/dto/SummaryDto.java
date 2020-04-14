package com.springboot.expencemanager.dto;

import java.util.List;

public class SummaryDto {

    private List<ExpenseUIDto> expenses;
    private Long sum;

    public List<ExpenseUIDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseUIDto> expenses) {
        this.expenses = expenses;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

}
