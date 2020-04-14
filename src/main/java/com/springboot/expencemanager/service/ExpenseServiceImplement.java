package com.springboot.expencemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.expencemanager.Entity.Expense;
import com.springboot.expencemanager.dao.ExpenseDAO;
import com.springboot.expencemanager.dto.ExpenseDTO;
import com.springboot.expencemanager.dto.SummaryDto;
import com.springboot.expencemanager.exceptions.RecordNotFoundException;

@Service
public class ExpenseServiceImplement implements ExpenseService {

    private Logger logger = LoggerFactory.getLogger(ExpenseServiceImplement.class);
    @Autowired
    private ExpenseDAO expenseDAO;
    private SummaryDto summaryDto;
    /**
     * Call addExpense method for adding new expenses
     */
    @Override
    public void addExpense(ExpenseDTO expenseDTO) {
        expenseDAO.addExpense(expenseDTO);
    }

    /**
     * Calling showExpense in expenseDAO for showing summary on Dashboard UI
     */
    public SummaryDto dashboard(int id) {
        logger.info("Id is {}", id);
        SummaryDto summary = expenseDAO.showExpense(id);
        if (summary == null) {
            throw new RecordNotFoundException("Invalid Id : " + id);
        }
        return summary;
    }

    /**
     * Delete Expense
     */
    public void deleteExpense(int id) {
        expenseDAO.delete(id);
    }

    /**
     * Sending mail info to executor service
     */
    public SummaryDto usingMultithread(int id) {
        ExecutorService executorService=  Executors.newFixedThreadPool(2);
        Runnable task1=new Runnable() {
            
            @Override
            public void run() {
                System.out.println("Thread name"+Thread.currentThread().getName());
                try {
                    summaryDto=expenseDAO.showExpense(id);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                
            }
        };
        executorService.submit(task1);
        return  summaryDto;
    }

    /**
     * Show all Expenses with all attributes
     */
    @Override
    public List<ExpenseDTO> showAllExpense(int id) {
        List<Expense> exp = expenseDAO.showAllExpense(id);
        List<ExpenseDTO> expDto = translateToDtos(exp);
        return expDto;
    }

    @Override
    public List<ExpenseDTO> translateToDtos(List<Expense> entity) {
        List<ExpenseDTO> expDto = new ArrayList<ExpenseDTO>();
        for (Object obj : entity) {
            Object[] obj1 = (Object[]) obj;
            ExpenseDTO dto = new ExpenseDTO();
            dto.setDate((java.sql.Date) obj1[1]);
            dto.setAmount((int) obj1[2]);
            dto.setTitle((String) obj1[0]);
            dto.setCategory((String) obj1[4]);
            dto.setDescription((String) obj1[3]);
            expDto.add(dto);
        }
        return expDto;
    }
}
