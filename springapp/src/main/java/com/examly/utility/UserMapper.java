package com.examly.utility;

import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;

public class UserMapper {
    public static LoginDTO mappedToLoginDTO(User user){
        String token="token"; // coming from jwtUtils
        LoginDTO loginDTO= new LoginDTO();
        loginDTO.setToken(token);
        loginDTO.setUserId(user.getUserId());
        loginDTO.setUsername(user.getUsername());
        loginDTO.setEmail(user.getEmail());
        loginDTO.setMobileNumber(user.getMobileNumber());
        loginDTO.setUserRole(user.getUserRole());
        return loginDTO;
    }
}
