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
    Showtime st = new Showtime();
    Booking b = new Booking();
    DatabaseOperation db = new DatabaseOperation();
    Movie m = new Movie();

    // Method to insert admin details
    public void insertAdmin() {
        String sql = "INSERT INTO user (USERID, username, password, isAdmin, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.connectToDatabase(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set values for the admin account
            ps.setInt(1, 56);  // Assuming 1 is the USERID for admin, can adjust based on auto increment
            ps.setString(2, "admin");
            ps.setString(3, "admin123");
            ps.setBoolean(4, true); // isAdmin flag set to true
            ps.setString(5, "1234567890");  // Admin phone number

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin added successfully.");
            } else {
                System.out.println("Failed to add admin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getUserIDFromUsername(String username) {
        String sql = "SELECT userID FROM users WHERE username = ?";
        int userID = -1; // Default value to handle case if user is not found
        try (Connection conn = db.connectToDatabase(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
                    m.showMovies();
                    System.out.print("Enter the movie name to you'd like to watch:  ");
                    sc.nextLine();  // Consume newline
                    String movieName = sc.nextLine();
                    m.showMovieDetails(movieName);


                case 2:
                    b.bookTicket(userID);
                    break;
                case 3:
                    b.cancelTicket(userID);
                    break;
                case 4:
                    System.out.println("Exiting user menu. Goodbye!");
                    return; // Exit user menu
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                    break;
            }
        }
    }

}




