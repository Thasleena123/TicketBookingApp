package src;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Booking {
    private DatabaseOperation db = new DatabaseOperation();
    private Scanner sc = new Scanner(System.in);
    Showtime st=new Showtime();

    public void bookTicket(int userID) {
        System.out.println("Available Showtimes:");


        System.out.println("Enter Showtime ID for booking:");
        int showtimeID = sc.nextInt();

        // Display available seats for the selected showtime
        Seat se = new Seat();
        se.showSeatsInScreen(showtimeID);

        System.out.println("Enter Seat ID to book:");
        int seatID = sc.nextInt();


        String sqlCheck = "SELECT * FROM seats WHERE seatID = ? AND isBooked = false";
        boolean isAvailable = false;
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
            ps.setInt(1, seatID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isAvailable = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }


        if (isAvailable) {
            // Proceed with booking
            String sql = "INSERT INTO booking (userID, showtimeID, seatID) VALUES (?, ?, ?)";
            Object[] values = {userID, showtimeID, seatID};
            int rowsAffected = db.executeUpdate(sql, values);

            if (rowsAffected > 0) {
                System.out.println("Ticket booked successfully!");

                // Update the seat status to booked
                updateSeatBooking(seatID, true);
            } else {
                System.out.println("Booking failed. Please try again.");
            }
        } else {
            System.out.println("Seat is already booked. Please choose another seat.");
        }
    }

    private void updateSeatBooking(int seatID, boolean isBooked) {
        String sql = "UPDATE seats SET isBooked = ? WHERE seatID = ?";
        Object[] values = {isBooked ? 1 : 0, seatID};
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println("Seat status updated to booked.");
        } else {
            System.out.println("Failed to update seat status.");
        }
    }
}


