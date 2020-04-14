package com.springboot.expencemanager.service;

import java.util.List;

import com.springboot.expencemanager.Entity.Expense;
import com.springboot.expencemanager.dto.ExpenseDTO;
import com.springboot.expencemanager.dto.SummaryDto;

public interface ExpenseService {

    public void addExpense(ExpenseDTO expenseDTO);

    public SummaryDto dashboard(int id);

    public void deleteExpense(int id);

    public SummaryDto usingMultithread(int id);

    public List<ExpenseDTO> showAllExpense(int id);

    public List<ExpenseDTO> translateToDtos(List<Expense> entity);
    

}
