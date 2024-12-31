package src;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Booking {
    private DatabaseOperation db = new DatabaseOperation();
    private Scanner sc = new Scanner(System.in);
    Showtime st = new Showtime();

    public void bookTicket(int userID) {


        System.out.println("Enter userID for booking:");
        int useID = sc.nextInt();
        System.out.println("Available Showtimes: ");
        st.showShowtimes();

        // Display available seats for the selected showtime
        System.out.println();
        Seat se = new Seat();
        System.out.println("enter a show time id ");
        int showtimeID=sc.nextInt();
        se. availableSeatsInShowtime(showtimeID);
        System.out.println("list of movies available in the thaeter");
        Movi m=new Movi();
        m.showMovies();
//        System.out.println("enter a screen id");
//        int screenID = sc.nextInt();
//
//         se.showSeatsInScreen(screenID);
        System.out.println("Enter Seat ID to book:");

        int seatID = sc.nextInt();

        String sqlCheck = "SELECT * FROM seats WHERE seat_id = ? AND isBooked = false";
        boolean isAvailable = false;
        ResultSet rs = null; // Declare ResultSet outside the try block
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
            ps.setInt(1, seatID);
            rs = ps.executeQuery(); // Initialize rs here
            if (rs.next()) {
                isAvailable = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (isAvailable) {
            // Proceed with booking
            String sql = "INSERT INTO booking (USERID,  seatID,SHOWID) VALUES (?, ?, ?)";
            Object[] values = {userID, seatID,showtimeID};
            int rowsAffected = db.executeUpdate(sql, values);

            if (rowsAffected > 0) {
                System.out.println("Ticket booked successfully!");

                // Update the seat status to booked
                System.out.println("here");
                updateSeatBooking(seatID, true);
            } else {
                System.out.println("Booking failed. Please try again.");
            }
        } else {
            System.out.println("Seat is already booked. Please choose another seat.");
        }
    }

    public void seeTicket(int userID) {
        System.out.println("Tickets Booked at different showtimes:");
        db.getAllBookingsForUser(userID);
//        System.out.print("Enter ShowtimeID to know information: ");
//        int showtimeID_choice = sc.nextInt();
//        st.showShowtimes(showtimeID);
    }

    public void cancelTicket(int userID) {
        System.out.println("Tickets Booked at different showtimes:");
        db.getAllBookingsForUser(userID);
        System.out.print("Enter BookingID to cancel Booking: ");

        //saving the bookings details
        int bookingID_choice = sc.nextInt();
        sc.nextLine();
        System.out.println("reason for cancelling ");
        String cancelReason=sc.nextLine();
        String sql = "UPDATE booking SET Status = 1, CANCELDATE = NOW(), CANCELACTIONREASON = ? WHERE BookingID = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cancelReason); // Set cancellation reason
            ps.setInt(2, bookingID_choice); // Set booking ID

            int rowsAffected = ps.executeUpdate(); // Execute update query
            if (rowsAffected > 0) {
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("Something went wrong. Booking not cancelled.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateSeatBooking(int seatID, boolean isBooked) {
        String sql = "UPDATE seats SET isBooked = ? WHERE seat_id = ?";
        Object[] values = {isBooked ? 1 : 0, seatID};
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println("Seat status updated to booked.");
        } else {
            System.out.println("Failed to update seat status.");
        }
    }
}





