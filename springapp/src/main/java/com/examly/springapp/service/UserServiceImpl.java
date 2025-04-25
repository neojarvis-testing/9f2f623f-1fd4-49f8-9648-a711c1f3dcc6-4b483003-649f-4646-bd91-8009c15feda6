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

 
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user=UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        User existingUser=userRepo.findByEmail(user.getEmail());
        if(existingUser!=null){
            throw new UserNotFoundException("User already exists!!!");
        }
        User savedUser = userRepo.save(user);
        return UserMapper.mapToUserDTO(savedUser);
    }

    @Override
    public LoginDTO loginUser(LoginDTO loginDTO) {
        User existingUser =  userRepo.findByEmail(loginDTO.getEmail());
        if(existingUser==null){
            throw new UserNotFoundException("User not found!!!");
        }
        if(existingUser.getEmail().equals(loginDTO.getEmail()) && encoder.matches(loginDTO.getPassword(), existingUser.getPassword())){
            return UserMapper.mappedToLoginDTO(existingUser);
        }
        throw new UserNotFoundException("Invalid Credentials");
    } 
    public List<User> getAllUsers() {
        return userRepo.findAll();
    } 
} 