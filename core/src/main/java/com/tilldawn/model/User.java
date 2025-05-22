package com.tilldawn.model;

public class User {
    private String username;
    private String password;
    private String securityAnswer;
    private String avatarPath;

    public User(String username, String password, String securityAnswer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.avatarPath = avatarPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
