package services;

import java.util.Scanner;

public class AuthService {
    // simple hard-coded credentials (you can extend to file later)
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "admin123";

    public boolean login(Scanner sc) {
        System.out.print("Enter admin username: ");
        String u = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String p = sc.nextLine().trim();
        if (ADMIN_USER.equals(u) && ADMIN_PASS.equals(p)) {
            System.out.println("Admin login successful.");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }
}
