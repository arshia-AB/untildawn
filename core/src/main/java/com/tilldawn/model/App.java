package com.tilldawn.model;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class App {
    private HashMap<String, User> AllUsers = new HashMap<>();
    private User currentUser = null;
    public static boolean grayscaleEnabled = false;
    public static Music currentMusic = null;
    private int timePassed = 0;
    private boolean SFX;

    public boolean isSFX() {
        return SFX;
    }

    public void setSFX(boolean SFX) {
        this.SFX = SFX;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public HashMap<String, User> getAllUsers() {
        return AllUsers;
    }

    public void setAllUsers(HashMap<String, User> allUsers) {
        AllUsers = allUsers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
