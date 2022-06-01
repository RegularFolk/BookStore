package com.bean;

public class User {
    private Integer userId;
    private String username;
    private String userPwd;
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User() {
    }

    public User(Integer userId, String username, String userPwd, String email) {
        this.userId = userId;
        this.username = username;
        this.userPwd = userPwd;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
