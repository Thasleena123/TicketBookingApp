package src;

import src.Booking;
import src.Showtime;

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
//        public void addAdminDetails() {
//            String sql = "INSERT INTO user (username, password, isAdmin, phone) VALUES (?, ?, ?, ?)";
//            try (Connection conn = db.connectToDatabase();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {
//                ps.setString(1, "admin");
//                ps.setString(2, "admin123");  // Consider hashing the password for security
//                ps.setBoolean(3, true);  // Set isAdmin to true for the admin account
//                ps.setString(4, "1234567890");  // Admin's phone number
//
//                int rowsAffected = ps.executeUpdate();
//                if (rowsAffected > 0) {
//                    System.out.println("Admin added successfully.");
//                } else {
//                    System.out.println("Failed to add admin.");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }


        // Method to insert admin details
        public void insertAdmin() {
            String sql = "INSERT INTO user (USERID, username, password, isAdmin, phone) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = db.connectToDatabase();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

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
                try (Connection conn = db.connectToDatabase();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
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
                System.out.println("---------- User Menu ----------");
                System.out.println("Press 1 to see all showtimes.");
                System.out.println("Press 2 to book a ticket");
                System.out.println("Press 3 to see ticket bookings");
                System.out.println("Press 4 to cancel ticket booking");
                System.out.println("Press 5 to exit.");
                System.out.print("Enter your choice: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    sc.next(); // Clear invalid input
                    continue;
                }

                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        st.showShowtimes();
                        break;
                    case 2:
                        b.bookTicket(userID);
                        break;
                    case 3:
                        b.seeTicket(userID);
                        break;
                    case 4:
                        b.cancelTicket(userID);
                        break;
                    case 5:
                        System.out.println("Exiting user menu. Goodbye!");
                        return; // Exit user menu
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                        break;
                }
            }
        }

    }



