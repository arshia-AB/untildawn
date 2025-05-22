package com.tilldawn.control;

import com.tilldawn.Main;
import com.tilldawn.model.Result;
import com.tilldawn.model.SaveUserToJson;
import com.tilldawn.model.User;

import static com.tilldawn.Main.*;

public class LoginController {
    public Result Login(String username, String password) {
        if (!Main.getApp().getAllUsers().containsKey(username)) {
            return new Result(false, "username doesn't exist");
        } else {
            if (!Main.getApp().getAllUsers().get(username).getPassword().equals(password)) {
                return new Result(false, "password is incorrect");
            } else {
                getApp().setCurrentUser(Main.getApp().getAllUsers().get(username));
                return new Result(true, "logged in successfully ");
            }
        }
    }

    public Result resetPassword(String username, String securityAnswer, String newPassword) {
        User user = Main.getApp().getAllUsers().get(username);
        if (user == null) {
            return new Result(false, "User not found!");
        }
        if (!user.getSecurityAnswer().equals(securityAnswer)) {
            return new Result(false, "Incorrect security answer!");
        }
        if (!isPasswordStrong(newPassword)) {
            return new Result(false, "password is weak");
        }
        user.setPassword(newPassword);
        SaveUserToJson.saveUserToJson(user);
        return new Result(true, "Password reset successful!");
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[@#$%&*()_].*");
    }
}
