package com.tilldawn.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class App {
    private HashMap<String, User> AllUsers = new HashMap<>();
    private User currentUser = null;

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
