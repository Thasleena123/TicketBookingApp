import src.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DatabaseOperation db = new DatabaseOperation();

        // Show the login or signup options
        System.out.println("----- Welcome to the Movie Booking System -----");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Signup");
        int choice = sc.nextInt();

        if (choice == 1) {
            // Login
            System.out.println("Enter your username:");
            sc.nextLine();  // Consume newline
            String username = sc.nextLine();

            System.out.println("Enter your password:");
            String password = sc.nextLine();

            // Authenticate the user
            if (db.authenticateUser(username, password)) {
                int userID = db.getUserID(username);
                System.out.println("Login successful! Your userID is: " + userID);
                // Check if the user is an admin
                if (db.isAdmin(userID)) {
                    Admin admin = new Admin();
                    admin.adminMenu(userID); // Show admin menu
                } else {
                    User user = new User();
                    user.userMenu(userID); // Show user menu
                }
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } else if (choice == 2) {
            // Signup
            System.out.println("Enter your username:");
            sc.nextLine();  // Consume newline
            String username = sc.nextLine();

            System.out.println("Enter your password:");
            String password = sc.nextLine();

            System.out.println("Enter your phone number:");
            String phone = sc.nextLine();

            // Add new user to the database
            boolean isRegistered = db.registerUser(username, password, phone);

            if (isRegistered) {
                System.out.println("Signup successful! You can now login. ");


                int userID = db.getUserID(username);
                System.out.println("Signup successful! Your userID is: " + userID);

                User user = new User();
                user.userMenu(userID);

            } else {
                System.out.println("Signup failed! Please try again.");
            }
        } else {
            System.out.println("Invalid choice. Exiting...");
        }
    }
}
