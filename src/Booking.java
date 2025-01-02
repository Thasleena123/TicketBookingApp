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
    Seat s=new Seat();


public void bookTicket(int userID) {
    System.out.println("Available Showtimes: ");
    st.showShowtimes(); // Display available showtimes
    Seat se = new Seat();
    // Display available seats for the selected showtime

    System.out.println("Enter a showtime ID: ");
    int showtimeID = sc.nextInt();
    se.availableSeatsInShowtime(showtimeID);

    // List movies available in the theater
    System.out.println("List of movies available in the theater: ");
    Movi m = new Movi();
    m.showMovies();

//    // Ask for seat ID to book
//    System.out.println("list of seats avilable");
//
//    s.availableSeatsInShowtime(showtimeID);

    System.out.println("Enter Seat ID to book: ");
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
        try {
            if (rs != null) rs.close(); // Close the ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    if (isAvailable) {
        // Proceed with booking
        String sql = "INSERT INTO booking (USERID, seatID, SHOWID) VALUES (?, ?, ?)";
        Object[] values = {userID, seatID, showtimeID};
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
    public void availableSeatsInShowtime(int showtimeID) {
        String sql = "SELECT seat_id, isBooked FROM seats WHERE SHOWID = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeID);
            ResultSet rs = ps.executeQuery();

            System.out.println("+------------+-----------------+");
            System.out.println("| Seat ID    | Status          |");
            System.out.println("+------------+-----------------+");
            while (rs.next()) {
                int seatID = rs.getInt("seat_id");
                boolean isBooked = rs.getBoolean("isBooked");
                String status = isBooked ? "Booked" : "Available";
                System.out.printf("| %-10d | %-15s |\n", seatID, status);
            }
            System.out.println("+------------+-----------------+");
        } catch (SQLException e) {
            e.printStackTrace();
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

//public void cancelTicket(int userID) {
//    System.out.println("Tickets Booked at different showtimes:");
//    ResultSet rs;  // Fetch the user's bookings
//    rs = db.getAllBookingsForUser(userID);
//
//    // Table header
//    System.out.println("+------------+----------------------+--------------------+---------------------+");
//    System.out.printf("| %-10s | %-20s | %-18s | %-19s |\n", "Booking ID", "Movie Title", "Showtime", "Booking Status");
//    System.out.println("+------------+----------------------+--------------------+---------------------+");
//
//    try {
//        boolean foundBookings = false;
//        while (rs.next()) {
//            int bookingID = rs.getInt("BookingID");
//            String movieTitle = rs.getString("movieTitle");
//            String showtime = rs.getString("showtime");  // Assuming it's a string; adjust accordingly
//            String status = rs.getString("Status");  // Assuming 0=Booked, 1=Cancelled, etc.
//
//            // Print booking details in table format
//            System.out.printf("| %-10d | %-20s | %-18s | %-19s |\n", bookingID, movieTitle, showtime, status);
//            foundBookings = true;
//        }
//
//        // If no bookings found
//        if (!foundBookings) {
//            System.out.println("| No bookings found for this user.                     |");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    } finally {
//        try {
//            if (rs != null) {
//                rs.close();  // Close ResultSet
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    // Table footer
//    System.out.println("+------------+----------------------+--------------------+---------------------+");
//    System.out.print("Enter BookingID to cancel Booking: ");
//
//    // Saving the bookings details
//    int bookingID_choice = sc.nextInt();
//    sc.nextLine();
//    System.out.println("Reason for cancelling: ");
//    String cancelReason = sc.nextLine();
//
//    String sql = "UPDATE booking SET Status = 1, CANCELDATE = NOW(), CANCELACTIONREASON = ? WHERE BookingID = ?";
//    try (Connection conn = db.connectToDatabase();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//        ps.setString(1, cancelReason); // Set cancellation reason
//        ps.setInt(2, bookingID_choice); // Set booking ID
//
//        int rowsAffected = ps.executeUpdate(); // Execute update query
//        if (rowsAffected > 0) {
//            System.out.println("Booking cancelled successfully.");
//        } else {
//            System.out.println("Something went wrong. Booking not cancelled.");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//public void availableSeatsInShowtime(int showtimeID) {
//    String sql = "SELECT seat_id FROM seats WHERE SHOWID = ? AND isBooked = false";
//    try (Connection conn = db.connectToDatabase();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//        ps.setInt(1, showtimeID);
//        ResultSet rs = ps.executeQuery();
//
//        System.out.println("+------------+-----------------+");
//        System.out.println("| Seat ID    | Status          |");
//        System.out.println("+------------+-----------------+");
//        while (rs.next()) {
//            int seatID = rs.getInt("seat_id");
//            System.out.printf("| %-10d | Available       |\n", seatID);
//        }
//        System.out.println("+------------+-----------------+");
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}


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





