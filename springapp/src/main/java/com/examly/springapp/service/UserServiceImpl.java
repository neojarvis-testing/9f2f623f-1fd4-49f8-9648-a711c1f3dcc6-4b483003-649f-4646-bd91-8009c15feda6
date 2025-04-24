package com.examly.springapp.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        user =  userRepo.findByEmail(user.getEmail());
        return UserMapper.mappedToLoginDTO(user);
    }
}