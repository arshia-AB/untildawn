package com.tilldawn.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.tilldawn.Main;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.tilldawn.Main.getMain;

public class SaveUserToJson {


    public static void saveUserToJson(User user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File("users.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<User> userList = new ArrayList<>();
        if (file.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                userList = gson.fromJson(reader, new TypeToken<List<User>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userList.removeIf(existingUser -> existingUser.getUsername().equals(user.getUsername()));
        userList.add(user);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() {
        try (FileReader reader = new FileReader("users.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Type userListType = new TypeToken<List<User>>() {
            }.getType();
            List<User> userList = gson.fromJson(reader, userListType);

            if (userList != null) {
                for (User user : userList) {
                    Main.getApp().getAllUsers().put(user.getUsername(), user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void removeUserFromJSON(String usernameToRemove) {
        try {

            FileReader reader = new FileReader("users.json");
            StringBuilder content = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
            reader.close();


            JSONArray usersArray = new JSONArray(content.toString());


            JSONArray updatedUsers = new JSONArray();
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (!user.getString("username").equals(usernameToRemove)) {
                    updatedUsers.put(user);
                }
            }
            FileWriter writer = new FileWriter("users.json");
            writer.write(updatedUsers.toString(2));
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
