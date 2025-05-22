package com.tilldawn.control;

import com.tilldawn.model.SaveUserToJson;
import com.tilldawn.model.User;

import java.util.*;

public class SignUpController {
    private Map<String, User> users = new HashMap<>();
    private static final String[] avatars = {
        "avatar1.png", "avatar2.png", "avatar3.png"
    };

    public String register(String username, String password, String securityAnswer) {
        if (users.containsKey(username)) {
            return "Username is already taken";
        }

        if (!isPasswordStrong(password)) {
            return "Weak password !";
        }

        String avatar = avatars[new Random().nextInt(avatars.length)];
        User user = new User(username, password, securityAnswer, avatar);
        users.put(username, user);
        SaveUserToJson.saveUserToJson(user);
        return "Registered successfully ";
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[@#$%&*()_].*");
    }

    public User loginAsGuest() {
        return new User("Guest", "", "", avatars[new Random().nextInt(avatars.length)]);
    }
}
