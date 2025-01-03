package src;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    String name;
    String username;
    String password;
    String address;
    String phone;
    int choice;
    Scanner sc = new Scanner(System.in);
    Showtime showtime = new Showtime();
    Booking booking = new Booking();
    DatabaseOperation databaseOperation = new DatabaseOperation();
    Movie movie = new Movie();

    public void checkAndCreateDefaultAdmin() {
        String checkAdminSQL = "SELECT COUNT(*) FROM user WHERE isAdmin = true";

        try (Connection conn = databaseOperation.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(checkAdminSQL)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {

                String insertAdminSQL = "INSERT INTO user (username, password, isAdmin, phone) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertPs = conn.prepareStatement(insertAdminSQL)) {
                    insertPs.setString(1, "admin");
                    insertPs.setString(2, "admin123");
                    insertPs.setBoolean(3, true);
                    insertPs.setString(4, "1234567890");
                    int rowsAffected = insertPs.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Default admin created successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addNewAdmin(int currentAdminID) {

        if (!databaseOperation.isAdmin(currentAdminID)) {
            System.out.println("You do not have permission to add an admin.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username for the new admin: ");
        String username = sc.nextLine();


        if (databaseOperation.usernameExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.println("Enter password for the new admin: ");
        String password = sc.nextLine();

        System.out.println("Enter phone number for the new admin: ");
        String phone = sc.nextLine();


        String sql = "INSERT INTO user (username, password, isAdmin, phone) VALUES (?, ?, ?, ?)";
        Object[] values = {username, password, true, phone};
        int rowsAffected = databaseOperation.executeUpdate(sql, values);

        if (rowsAffected > 0) {
            System.out.println("New admin added successfully.");
        } else {
            System.out.println("Failed to add new admin.");
        }
    }





    public int getUserIDFromUsername(String username) {
        String sql = "SELECT userID FROM users WHERE username = ?";
        int userID = -1;
        try (Connection conn = databaseOperation.connectToDatabase(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userID = rs.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }

    public void userMenu(int userID) {
        while (true) {
            System.out.println("----------  User Menu  ----------");
            System.out.println("🎬 1. Check Out the Movies Available Now ");
            System.out.println("🎟️ 2. Book a Ticket ");
            System.out.println("❌ 3. Cancel Your Ticket Booking ");
            System.out.println("👋 4. Exit the App ");
            System.out.print("👉 Please enter your choice (1-4): ");

            //--------------------------------------------------------------------------------------------------

            if (!sc.hasNextInt()) {
                System.out.println(" Invalid input! Please enter a number between 1 and 4.");
                sc.next(); // Clear invalid input
                continue;
            }

            choice = sc.nextInt();


            switch (choice) {
                case 1:
                    movie.showMovies();
                    System.out.print("Enter the movie name to you'd like to watch:  ");
                    sc.nextLine();  // Consume newline
                    String movieName = sc.nextLine();
                    movie.showMovieDetails(movieName);


                case 2:
                    booking.bookTicket(userID);
                    break;
                case 3:
                    booking.cancelTicket(userID);
                    break;
                case 4:
                    System.out.println("Exiting user menu. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                    break;
            }
        }
    }

}




