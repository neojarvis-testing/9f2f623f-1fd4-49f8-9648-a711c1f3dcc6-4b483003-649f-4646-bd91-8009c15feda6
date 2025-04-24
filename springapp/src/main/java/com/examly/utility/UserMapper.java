package com.examly.utility;

import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;

public class UserMapper {
    public static LoginDTO mappedToLoginDTO(User user){
        String token="token"; // coming from jwtUtils
        LoginDTO loginDTO= new LoginDTO();
        loginDTO.setToken(token);
        loginDTO.setUserId(user.getUserId());
        loginDTO.setUserRole(user.getUserRole());
        loginDTO.setUsername(user.getUsername());
        return loginDTO;
    }
}
