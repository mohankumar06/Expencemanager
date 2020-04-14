package com.springboot.expencemanager.dao;

import java.util.List;
import org.hibernate.query.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.springboot.expencemanager.Entity.Expense;
import com.springboot.expencemanager.Entity.User;
import com.springboot.expencemanager.conversion.Translator;
import com.springboot.expencemanager.dto.ExpenseDTO;
import com.springboot.expencemanager.dto.ExpenseUIDto;
import com.springboot.expencemanager.dto.SummaryDto;
import com.springboot.expencemanager.exceptions.RecordNotFoundException;

@Repository
@Transactional
public class ExpenseDAOImplement implements ExpenseDAO, Translator<Expense, ExpenseDTO> {

    private Logger logger = LoggerFactory.getLogger(ExpenseDAOImplement.class);
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserDAO userDAO;

    /**
     * Add new Expenses DAO Implemenatation
     */
    @Override
    public void addExpense(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        translateToEntity(expense, expenseDTO);
        entityManager.persist(expense);
    }

    /** Conversion Method DTO to Entity */
    @Override
    public Expense translateToEntity(Expense entity, ExpenseDTO dto) {
        entity.setUsers(userDAO.findUser(dto.getUser_id()));
        entity.setTitle(dto.getTitle());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setAmount(dto.getAmount());
        return entity;
    }

    /** Conversion Method Entity to DTO */
    @Override
    public ExpenseDTO translateToDTO(Expense entity, ExpenseDTO dto) {
        dto.setTitle(entity.getTitle());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setDate(entity.getDate());
        dto.setAmount(entity.getAmount());
        return dto;
    }

    /**
     * Dashboard UI (Display all Expenses,month and year for each particular user)
     */
    public SummaryDto showExpense(int id) {
        User user = userDAO.findUser(id);
        logger.debug("User Id for showing expenses {}", id);
        Query query = (Query) entityManager.createQuery("select DATE_FORMAT(Date,'%b %Y') as "
                + "MonthAndYear,sum(amount) as Sum from Expense where users.user_id = " + id
                + " group by month(Date), year(Date)");
        List<ExpenseUIDto> expenseList = query.list();
        Query query1 = (Query) entityManager
                .createQuery("select sum(amount) from Expense " + "where users.user_id = " + id);
        if (query1.getResultList().get(0) != null) {
            SummaryDto summary = new SummaryDto();
            summary.setExpenses(expenseList);
            summary.setSum((Long) query1.getResultList().get(0));
            return summary;
        }
        return null;
    }

    /**
     * Delete Method
     */
    public void delete(int id) {
        Expense exp = entityManager.find(Expense.class, id);
        if (exp == null) {
            throw new RecordNotFoundException("Invalid Id : " + id);
        }
        entityManager.remove(exp);
    }

    /** Display all expenses after clicking on viewAllButton */
    @Override
    public List<Expense> showAllExpense(int id) {
        Query query = (Query) entityManager.createQuery("select title,Date,amount,description,"
                + "category,expense_id from Expense where users.user_id = " + id);
        List<Expense> expenseList = query.list();
        if(expenseList != null)
        {
            query.setFirstResult(0);
            query.setMaxResults(10);
            logger.debug("List of all expenses with all attributes");
            return expenseList;
        }
        return null;
    }

}
