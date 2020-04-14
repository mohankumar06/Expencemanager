package com.springboot.expencemanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.expencemanager.dao.UserDAO;
import com.springboot.expencemanager.dto.UserDTO;

@Service
public class UserServiceImplement implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImplement.class);
    @Autowired
    private UserDAO userDAO;

    /* This method is used to add new user */
    @Override
    @Transactional
    public String addUser(UserDTO userDTO) {

        if (userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            userDAO.addUser(userDTO);
            logger.info("User added successfully");
            return "Successfully Added";
        } else
        {
            logger.error("Password not match");
            return "Password Not match";
        }
    }

    /* Login method */
    @Override
    public ResponseEntity<String> loginUser(UserDTO userDTO) throws InterruptedException {

        List<String> pwd = userDAO.loginUser(userDTO);

        if (!pwd.isEmpty() && pwd.get(0).equals(userDTO.getPassword())) {
            return new ResponseEntity<String>("Successfully Login", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Successfully Not Login", HttpStatus.UNAUTHORIZED);

    }

}
