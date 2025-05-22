package com.tilldawn.control;

import com.tilldawn.Main;
import com.tilldawn.model.Result;
import com.tilldawn.model.User;

import java.sql.PreparedStatement;

import static com.tilldawn.model.App.*;

import static com.tilldawn.model.App.*;

public class ProfileController {
    public Result ChangeUsername(String username) {
        for (User user : Main.getApp().getAllUsers().values()) {
            if (user.getUsername().equals(username)) return new Result(false, "username is already taken");
        }
        Main.getApp().getCurrentUser().setUsername(username);
        return new Result(true, "username changed");

    }

    public Result changePassword(String password) {
        if (!isPasswordStrong(password)) {
            return new Result(false, "password is weak");

        }
        Main.getApp().getCurrentUser().setUsername(password);
        return new Result(true, "password changed");

    }

    public Result DeleteAccount() {
        return new Result(true, "Account deleted successfully");
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[@#$%&*()_].*");
    }
}
