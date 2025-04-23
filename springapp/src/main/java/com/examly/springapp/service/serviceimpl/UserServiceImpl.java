package com.examly.springapp.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.service.UserService;

@Service
public class UserServiceImpl implements UserService{
@Autowired
UserRepo userRepo;
    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }
}
