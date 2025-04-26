package com.examly.springapp.service;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.config.UserPrinciple;
import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override 
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        User existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new UserNotFoundException("User already exists!!!");
        }
        User savedUser = userRepo.save(user);
        // logger.info("User created successfully: {}", savedUser);
        return UserMapper.mapToUserDTO(savedUser);
    }

    @Override
    public LoginDTO loginUser(LoginDTO loginDTO) {
        User existingUser = userRepo.findByEmail(loginDTO.getEmail());
        if (existingUser == null) {
            throw new UserNotFoundException("User not found!!!");
        }
        if (encoder.matches(loginDTO.getPassword(), existingUser.getPassword())) {
            return new LoginDTO(
                null, // Token will be set later in the controller
                existingUser.getUsername(),
                null, // Do not include the password in the response
                existingUser.getUserRole(),
                existingUser.getUserId(),
                existingUser.getEmail(),
                existingUser.getMobileNumber()
            );
        }
        // logger.error("Invalid credentials for user with email: {}", loginDTO.getEmail());
        throw new UserNotFoundException("Invalid Credentials");
    }

    @Override
    public List<User> getAllUsers() {
        // logger.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        // return new org.springframework.security.core.userdetails.User(
        //     user.getEmail(),
        //     user.getPassword(),
        //     List.of(new SimpleGrantedAuthority("ROLE_USER"))
        // );
        return UserPrinciple.build(user);
    }
}
