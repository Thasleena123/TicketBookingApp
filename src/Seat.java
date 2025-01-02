package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Seat {
    private DatabaseOperation db = new DatabaseOperation();
    private Scanner sc = new Scanner(System.in);

    public void insertSeats(int screenId, String level, int numSeats, int showtimeId) {
        for (int seatNumber = 1; seatNumber <= numSeats; seatNumber++) {
            String sql = "INSERT INTO seats (screen_id, level, seat_number, isBooked, seat_status, showtime_id) VALUES (?, ?, ?, ?, ?, ?)";
            Object[] values = {screenId, level, seatNumber, false, "available", showtimeId};
            db.executeUpdate(sql, values);
        }
        System.out.println("Seats added successfully for level " + level + " and showtime ID " + showtimeId + "!");
    }


    public void showSeatsInScreen(int screenId) {
        String sql = "SELECT * FROM seats WHERE screen_id = ?";
        ResultSet rs = db.getRecords(sql, screenId);

        System.out.println("Seats in Screen ID: " + screenId);

        try {
            if (!rs.isBeforeFirst()) { // Check if there are any results
                System.out.println("No seats found for this screen.");
                return;
            }

            // Print table header
            System.out.println("+---------+--------------+---------+");
            System.out.printf("| %-7s | %-12s | %-7s |\n", "Level", "Seat Number", "Booked");
            System.out.println("+---------+--------------+---------+");

            // Print table rows
            while (rs.next()) {
                String level = rs.getString("level");
                int seatNumber = rs.getInt("seat_number");
                boolean isBooked = rs.getBoolean("isBooked");

                System.out.printf("| %-7s | %-12d | %-7s |\n", level, seatNumber, isBooked ? "Yes" : "No");
            }

            // Print table footer
            System.out.println("+---------+--------------+---------+");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close(); // Close the ResultSet
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //--------------------------------------------------------------------------------------------------------
  public  boolean isSeatAvailable(int seatID) {
        String sql = "SELECT isBooked FROM seats WHERE seat_id = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, seatID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return !rs.getBoolean("isBooked"); // Return true if not booked
            }
        } catch (SQLException e) {
            System.out.println("❌ Error checking seat availability: " + e.getMessage());
        }
        return false; // Default to unavailable if query fails
    }


//------------------------------------------------------------------------------------------------------------
    public void availableSeatsInShowtime(int showtimeID) {
        String sql = "SELECT * FROM seats WHERE showtime_id = ? AND isBooked = false";
        ResultSet rs = db.getRecords(sql, showtimeID);

        System.out.println("Available Seats for Showtime ID: " + showtimeID);

        try {
            if (!rs.isBeforeFirst()) { // Check if there are any results
                System.out.println("No available seats for this showtime.");
                return;
            }

            // Print table header
            System.out.println("+---------+---------+--------------+");
            System.out.printf("| %-7s | %-7s | %-12s |\n", "Seat ID", "Level", "Seat Number");
            System.out.println("+---------+---------+--------------+");

            // Print table rows
            while (rs.next()) {
                int seatID = rs.getInt("seat_id");
                String level = rs.getString("level");
                int seatNumber = rs.getInt("seat_number");

                System.out.printf("| %-7d | %-7s | %-12d |\n", seatID, level, seatNumber);
            }

            // Print table footer
            System.out.println("+---------+---------+--------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    //--------------------------------------------------------------------------------------------------
        public void updateSeatBooking ( int screenId, int seatNumber, boolean isBooked, String seat_status){

            String sql = "UPDATE seats SET isBooked = ?, seat_status = ? WHERE screen_id = ? AND seat_number = ?";


            Object[] values = {isBooked ? 1 : 0, seat_status, screenId, seatNumber};


            int rowsAffected = db.executeUpdate(sql, values);


            if (rowsAffected > 0) {
                if (isBooked) {
                    System.out.println("Seat " + seatNumber + " booked successfully!");
                } else {
                    System.out.println("Booking for seat " + seatNumber + " canceled successfully!");
                }
            } else {
                System.out.println("No seat found with the specified details.");
            }
        }


}



