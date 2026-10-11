package com.inventory.ui;

import com.inventory.dao.UserDao;
import com.inventory.model.User;

import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final UserDao userDao = new UserDao();
    private User loggedInUser;

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== COMPUTER PARTS INVENTORY SYSTEM ===");
            System.out.println("1. Register Account");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> logout();
                case "4" -> {
                    if (loggedInUser != null) {
                        logout();
                    }

                    running = false;
                    System.out.println("Application closed.");
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password are required.");
            return;
        }

        System.out.println("Select role:");
        System.out.println("1. TEACHER");
        System.out.println("2. STUDENT");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine();
        String role;

        if (choice.equals("1")) {
            role = "TEACHER";
        } else if (choice.equals("2")) {
            role = "STUDENT";
        } else {
            System.out.println("Invalid role.");
            return;
        }

        boolean registered =
                userDao.register(username, password, role);

        if (registered) {
            System.out.println("Account registered successfully.");
            System.out.println("Account status: PENDING");
        } else {
            System.out.println(
                    "Registration failed. Username may already exist.");
        }
    }

    private void login() {
        if (loggedInUser != null) {
            System.out.println("You are already logged in.");
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<User> result = userDao.login(username, password);

        if (result.isPresent()) {
            loggedInUser = result.get();

            System.out.println("Login successful!");
            System.out.println("Welcome, " + loggedInUser.username());
            System.out.println("Role: " + loggedInUser.roles());
            System.out.println("Status: " + loggedInUser.status());
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void logout() {
        if (loggedInUser != null) {
            System.out.println(
                    loggedInUser.username() + " logged out.");
            loggedInUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}