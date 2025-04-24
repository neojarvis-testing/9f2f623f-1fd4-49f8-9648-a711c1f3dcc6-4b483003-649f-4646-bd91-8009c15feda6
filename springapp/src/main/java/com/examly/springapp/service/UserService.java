package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.User;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    LoginDTO loginUser(LoginDTO loginDTO);
    List<User> getAllUsers();
    
}
