package com.springboot.expencemanager.dao;

import java.util.List;

import com.springboot.expencemanager.Entity.User;
import com.springboot.expencemanager.dto.UserDTO;

public interface UserDAO {

    public void addUser(UserDTO userDTO);

    public List<String> loginUser(UserDTO userDTO) throws InterruptedException;

    public User findUser(int userId);

}
