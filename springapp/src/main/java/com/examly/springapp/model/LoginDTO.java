package com.examly.springapp.model;

public class LoginDTO {
    private String token;      
    private String username;        
    private String userRole;        
    private long userId;
    private String email;
    private String mobileNumber;
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public LoginDTO(String token, String username, String userRole, long userId) {
        this.token = token;
        this.username = username;
        this.userRole = userRole;
        this.userId = userId;
    }
    public LoginDTO(){

    }
}
