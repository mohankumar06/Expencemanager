package com.springboot.expencemanager.service;

import org.springframework.http.ResponseEntity;
import com.springboot.expencemanager.dto.UserDTO;

public interface UserService {

    public String addUser(UserDTO userDTO);

    public ResponseEntity<String> loginUser(UserDTO userDTO) throws InterruptedException;

}
