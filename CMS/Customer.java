package CMS;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private List<String> usernames;
    private List<String> passwords;
    private List<Double> loyaltyPoints;

    public Customer() {
        this.usernames = new ArrayList<>();
        this.passwords = new ArrayList<>();
        this.loyaltyPoints = new ArrayList<>();
    }

    public boolean addCustomer(String username, String password) {
        if (usernames.contains(username)) {
            return false;
        }
        usernames.add(username);
        passwords.add(hashPassword(password)); // Hash password before storing
        loyaltyPoints.add(0.0); // Initial loyalty points
        return true;
    }

    public boolean verifyCredentials(String username, String password) {
        int index = usernames.indexOf(username);
        return index != -1 && checkPassword(password, passwords.get(index)); // Verify hashed password
    }

    public double getLoyaltyPoints(String username) {
        int index = usernames.indexOf(username);
        if (index == -1) {
            throw new IllegalArgumentException("Username not found: " + username);
        }
        return loyaltyPoints.get(index);
    }

    public void updateLoyaltyPoints(String username, double points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative.");
        }
        int index = usernames.indexOf(username);
        if (index != -1) {
            loyaltyPoints.set(index, loyaltyPoints.get(index) + points);
        }
    }

    private String hashPassword(String password) {
        // Use a secure hashing algorithm (e.g., BCrypt)
        return password; // Replace with actual hashing logic
    }

    private boolean checkPassword(String inputPassword, String hashedPassword) {
        // Verify hashed password
        return inputPassword.equals(hashedPassword); // Replace with actual verification logic
    }
}