package com.tilldawn.model;

import com.tilldawn.Enum.HeroEnum;
import com.tilldawn.Enum.WeaponEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY, " +
                "hero TEXT, " +
                "weapon TEXT)";
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveUser(User user) {
        String sql = "REPLACE INTO users (username, hero, weapon) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getHero().name());
            pstmt.setString(3, user.getWeaponEnum().name());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> loadUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//                User user = new User();
//                user.setUsername(rs.getString("username"));
//                user.setHero(HeroEnum.valueOf(rs.getString("hero")));
//                user.setWeapon(WeaponEnum.valueOf(rs.getString("weapon")));//todo
//                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
