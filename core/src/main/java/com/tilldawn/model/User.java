package com.tilldawn.model;

import com.tilldawn.Enum.Hero;
import com.tilldawn.Enum.Weapon;

public class User {
    private String username;
    private String password;
    private String securityAnswer;
    private String avatarPath;
    private int score = 0;
    private Hero hero;
    private Weapon weapon;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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
