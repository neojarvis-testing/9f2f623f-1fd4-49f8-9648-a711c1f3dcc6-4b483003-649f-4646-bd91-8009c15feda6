package com.examly.springapp.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
@Tag(name = "Authentication", description = "APIs for user authentication and registration")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
     @Autowired
    private AuthenticationManager authenticationManager;

    
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    @PostMapping("register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        userDTO = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(userDTO);
    }
    @Operation(summary = "Authenticate and log in a user", description = "Authenticates user credentials and generates a JWT token.")
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.genrateToken(authentication);

        LoginDTO existingUserDTO = userService.loginUser(loginDTO);
        if (existingUserDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Set the token in the response DTO
        existingUserDTO.setToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(existingUserDTO);
    }
    
    @Operation(summary = "Get Users from Database", description = "Get All Users from the Database.")
    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> list = userService.getAllUsers();
        return ResponseEntity.status(200).body(list); 
    }
}




 