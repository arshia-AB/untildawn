package com.tilldawn.control;

import com.tilldawn.Main;
import com.tilldawn.model.Result;
import com.tilldawn.model.User;

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
}
