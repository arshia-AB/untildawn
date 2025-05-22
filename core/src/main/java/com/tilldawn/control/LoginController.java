package com.tilldawn.control;

import com.tilldawn.Main;
import com.tilldawn.model.Result;

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
}
