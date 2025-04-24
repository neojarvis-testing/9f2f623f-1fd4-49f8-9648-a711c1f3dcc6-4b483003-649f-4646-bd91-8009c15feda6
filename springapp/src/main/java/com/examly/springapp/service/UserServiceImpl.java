package com.examly.springapp.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.utility.UserMapper;
 
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;
    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    public LoginDTO loginUser(User user) {
        User loginData =  userRepo.findByEmail(user.getEmail());
        if(loginData==null){
            throw new UserNotFoundException("User not found!!!");
        }
        if(loginData.getEmail().equals(user.getEmail()) && loginData.getPassword().equals(user.getPassword())){
            user=userRepo.findByEmail(user.getEmail());
            // System.out.println(user.getEmail());
            return UserMapper.mappedToLoginDTO(user);
        }
        throw new UserNotFoundException("Invalid Credentials");
    } 
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}