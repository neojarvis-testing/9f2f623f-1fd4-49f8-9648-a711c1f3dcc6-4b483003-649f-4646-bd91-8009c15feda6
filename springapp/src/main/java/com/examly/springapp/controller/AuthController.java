package com.examly.springapp.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;
// import com.examly.springapp.service.UserServiceImpl;

@RestController
@RequestMapping("/api/")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        user = userService.createUser(user);
        return ResponseEntity.status(201).body(user);
    }

    @PostMapping("login")
    public ResponseEntity<LoginDTO> loginUser(@RequestBody User user){
        LoginDTO loginDTO = userService.loginUser(user);
        return ResponseEntity.status(200).body(loginDTO);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> list = userService.getAllUsers();
        return ResponseEntity.status(200).body(list); 
    }
}
