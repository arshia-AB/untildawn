package com.tilldawn.control;

import com.tilldawn.Main;
import com.tilldawn.model.Result;
import com.tilldawn.model.SaveUserToJson;
import com.tilldawn.model.User;

import static com.tilldawn.model.App.*;

import java.util.*;

public class SignUpController {
    private static final String[] avatars = {"avatar1.png", "avatar2.png", "avatar3.png"};

    public Result register(String username, String password, String securityAnswer) {
        if (Main.getApp().getAllUsers().containsKey(username)) {
            return new Result(false, "Username is already taken");
        }

        if (!isPasswordStrong(password)) {
            return new Result(false, "Weak password !");
        }

        String avatar = avatars[new Random().nextInt(avatars.length)];
        User user = new User(username, password, securityAnswer, avatar);
        Main.getApp().getAllUsers().put(username, user);
        SaveUserToJson.saveUserToJson(user);
        return new Result(true, "Registered successfully ");
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[@#$%&*()_].*");
    }

    public User loginAsGuest() {
        return new User("Guest", "", "", avatars[new Random().nextInt(avatars.length)]);
    }
}
