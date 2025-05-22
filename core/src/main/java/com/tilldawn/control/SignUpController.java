package com.tilldawn.control;

import com.tilldawn.model.User;

import java.util.*;

public class SignUpController {
    private Map<String, User> users = new HashMap<>();
    private static final String[] avatars = {
        "avatars/avatar1.png", "avatars/avatar2.png", "avatars/avatar3.png"
    };

    public String register(String username, String password, String securityAnswer) {
        if (users.containsKey(username)) {
            return "نام کاربری قبلاً ثبت شده است.";
        }

        if (!isPasswordStrong(password)) {
            return "رمز عبور باید حداقل ۸ کاراکتر، یک عدد، یک حرف بزرگ و یک کاراکتر خاص داشته باشد.";
        }

        String avatar = avatars[new Random().nextInt(avatars.length)];
        users.put(username, new User(username, password, securityAnswer, avatar));
        return "ثبت‌نام با موفقیت انجام شد.";
    }

    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[@#$%&*()_].*");
    }

    public User loginAsGuest() {
        return new User("مهمان", "", "", avatars[new Random().nextInt(avatars.length)]);
    }
}
