package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Creating user: {}", userDTO);
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            logger.error("User already exists with email: {}", user.getEmail());
            throw new UserNotFoundException("User already exists!!!");
        }
        User savedUser = userRepo.save(user);
        logger.info("User created successfully: {}", savedUser);
        return UserMapper.mapToUserDTO(savedUser);
    }

    @Override
    public LoginDTO loginUser(LoginDTO loginDTO) {
        logger.info("Logging in user with email: {}", loginDTO.getEmail());
        User existingUser = userRepo.findByEmail(loginDTO.getEmail());
        if (existingUser == null) {
            logger.error("User not found with email: {}", loginDTO.getEmail());
            throw new UserNotFoundException("User not found!!!");
        }
        if (existingUser.getEmail().equals(loginDTO.getEmail()) && encoder.matches(loginDTO.getPassword(), existingUser.getPassword())) {
            logger.info("User logged in successfully: {}", existingUser);
            return UserMapper.mappedToLoginDTO(existingUser);
        }
        logger.error("Invalid credentials for user with email: {}", loginDTO.getEmail());
        throw new UserNotFoundException("Invalid Credentials");
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepo.findAll();
    }
}