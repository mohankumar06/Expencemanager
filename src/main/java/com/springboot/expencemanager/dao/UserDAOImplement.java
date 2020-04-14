package com.springboot.expencemanager.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.springboot.expencemanager.Entity.Expense;
import com.springboot.expencemanager.Entity.User;
import com.springboot.expencemanager.conversion.Translator;
import com.springboot.expencemanager.dto.UserDTO;

@Repository
@Transactional
public class UserDAOImplement implements UserDAO, Translator<User, UserDTO> {

    private Logger logger = LoggerFactory.getLogger(UserDAOImplement.class);

    @Autowired
    private EntityManager entityManager;

    /* Method for add  new user */
    @Override
    public void addUser(UserDTO userDTO) {
        User user = new User();
        translateToEntity(user, userDTO);
        entityManager.persist(user);

    }

    /* Method for login */
    @Override
    @Async
    public List<String> loginUser(UserDTO userDTO) throws InterruptedException {
        User user = new User();
        translateToEntity(user, userDTO);
        Query theQuery = (Query) entityManager.createQuery("select password from User "
                + "where username = '" + user.getUsername() + "'");
        List<String> pwd = theQuery.list();
        logger.info("Password validation in Login");
        Thread.sleep(1000);
        return pwd;

    }

    /*
     * Method for Find User ID
     */    public User findUser(int userId) {
        logger.warn("Finding user");
        return entityManager.find(User.class, userId);
    }

    /* Conversion Method DTO to Entity */
    @Override
    public User translateToEntity(User entity, UserDTO dto) {
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    /*
     * Conversion Method Entity to DTO
     */
    @Override
    public UserDTO translateToDTO(User entity, UserDTO dto) {
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
