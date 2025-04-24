package com.examly.springapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.User;
import com.examly.springapp.service.UserServiceImpl;

@RestController
public class AuthController {
@Autowired
UserServiceImpl userService;

@PostMapping("/api/register")
public ResponseEntity<?> createUser(@RequestBody User user){
    user = userService.createUser(user);
    return ResponseEntity.status(201).body(user);
}

}
