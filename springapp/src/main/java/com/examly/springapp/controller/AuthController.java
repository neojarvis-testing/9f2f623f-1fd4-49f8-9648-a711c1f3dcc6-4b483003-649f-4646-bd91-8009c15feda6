package com.examly.springapp.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        userDTO = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(userDTO);
    }

    @PostMapping("login")
    public ResponseEntity<LoginDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO){
        loginDTO = userService.loginUser(loginDTO);
        return ResponseEntity.status(200).body(loginDTO);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> list = userService.getAllUsers();
        return ResponseEntity.status(200).body(list); 
    }
}
