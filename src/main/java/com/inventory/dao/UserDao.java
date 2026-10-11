package com.inventory.dao;

import com.inventory.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    // Register a new account
    public boolean register(
            String username, String password, String role) {

        String sql = """
                INSERT INTO users (username, password, roles)
                VALUES (?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(
                    "Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Verify login credentials
    public Optional<User> login(
            String username, String password) {

        String sql = """
                SELECT id, username, roles, status
                FROM users
                WHERE username = ? AND password = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    User user = new User(
                            result.getInt("id"),
                            result.getString("username"),
                            result.getString("roles"),
                            result.getString("status")
                    );

                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Login error: " + e.getMessage());
        }

        return Optional.empty();
    }
}
