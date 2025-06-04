package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.model.Result;
import com.tilldawn.model.SaveUserToJson;
import com.tilldawn.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.security.SecureRandom;
import java.util.Random;

import com.tilldawn.Enum.*;

import static com.tilldawn.model.App.*;

import java.util.*;

public class SignUpController {
    public String getRandomAvatar() {
        SecureRandom secureRandom = new SecureRandom();
        int random = secureRandom.nextInt(27);
        return Avatar.fromId(random).getPath();
    }

    public Result register(String username, String password, String securityAnswer) {
        if (Main.getApp().getAllUsers().containsKey(username)) {
            return new Result(false, "Username is already taken");
        }

        if (!isPasswordStrong(password)) {
            return new Result(false, "Weak password !");
        }

        String avatar = getRandomAvatar();
        User user = new User(username, password, securityAnswer, avatar);
        Main.getApp().getAllUsers().put(username, user);
        SaveUserToJson.saveUserToJson(user);
        Main.getApp().setCurrentUser(user);
        return new Result(true, "Registered successfully ");
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[@#$%&*()_].*");
    }

    public User loginAsGuest() {
        int maxGuestNumber = 0;

        for (String username : Main.getApp().getAllUsers().keySet()) {
            if (username.startsWith("Guest")) {
                try {
                    String numberPart = username.replace("Guest", "");
                    if (!numberPart.isEmpty()) {
                        int num = Integer.parseInt(numberPart);
                        if (num > maxGuestNumber) {
                            maxGuestNumber = num;
                        }
                    } else {

                        if (maxGuestNumber == 0) {
                            maxGuestNumber = 1;
                        }
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        String newGuestUsername = "Guest" + (maxGuestNumber + 1);
        User guestUser = new User(newGuestUsername, "", "", getRandomAvatar());
        Main.getApp().getAllUsers().put(newGuestUsername, guestUser);
        return guestUser;
    }
}
