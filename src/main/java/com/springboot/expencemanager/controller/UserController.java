package com.springboot.expencemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.expencemanager.dto.UserDTO;
import com.springboot.expencemanager.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * User registration API 
     * Input:Username,
     *       password,
     *       confirmPassword
     * Output:Register successfully
     * @param userDTO
     * @return User Details 
     */
    @PostMapping("/register")
    public String addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);

    }


    /**
     * Login API 
     * Input:Username,
     *       password
     * Output:
     *  Login Successfully
     * @param userDTO
     * @return userDTO(Username and Password)
     * @throws InterruptedException 
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) throws InterruptedException {
        logger.info("User name: {}", userDTO.getUsername());
        logger.debug("Password: {}", userDTO.getPassword());
        return userService.loginUser(userDTO);
    }

}
