package src;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Booking extends  User {
    public DatabaseOperation db = new DatabaseOperation();
     Scanner sc = new Scanner(System.in);
    Showtime st = new Showtime();
    Seat s = new Seat();

    public void bookTicket(int userID) {
        //  Show available movies
        System.out.println("🎬 Available Movies: ");
        Movie m = new Movie();
        m.showMovies();

        //  Prompt the user to select a movie
        System.out.print("Enter the Movie ID you'd like to watch: ");
        int movieID = sc.nextInt();

        // Show theaters, screens, and showtimes for the selected movie
        System.out.println("🎭 Theaters and Showtimes for the selected movie:");
        showTheaterAndScreenDetails(movieID);

        //  the user to select a showtime
        System.out.print("Enter the Show ID to proceed: ");
        int showID = sc.nextInt();

        //  Show available seats for the selected showtime
        System.out.println("💺 Available Seats for the selected showtime:");
        Seat se = new Seat();
        se.availableSeatsInShowtime(showID);

        //  Prompt the user to select a seat
        System.out.print("Enter Seat ID to book: ");
        int seatID = sc.nextInt();

        // Check if the seat is available
        if (!se.isSeatAvailable(seatID)) {
            System.out.println("❌ Seat is already booked. Please choose another seat.");
            return;
        }

        //  Book the ticket
        String sql = "INSERT INTO booking (USERID, seatID, SHOWID, STATUS) VALUES (?, ?, ?, 'BOOKED')";
        Object[] values = {userID, seatID, showID};
        int rowsAffected = db.executeUpdate(sql, values);

        if (rowsAffected > 0) {
            System.out.println("✅ Ticket booked successfully!");
            updateSeatBooking(seatID, true);
        } else {
            System.out.println("❌ Booking failed. Please try again.");
        }
    }

//-------------------------------------------------------------------------------------
public void availableSeatsInShowtime(int showtimeID) {
    String sql = "SELECT seat_id, isBooked FROM seats WHERE SHOWID = ?";
    boolean hasAvailableSeats = false;

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
            if (!isBooked) {
                hasAvailableSeats = true;  // If there are any available seats, mark it as true
            }
            System.out.printf("| %-10d | %-15s |\n", seatID, status);
        }
        System.out.println("+------------+-----------------+");
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // If no available seats, inform the user and exit the method
    if (!hasAvailableSeats) {
        System.out.println("No available seats for this showtime.");
        return;
    }
}

    //--------------------------------------------------------------------------------------------
    public void updateSeatBooking(int seatID, boolean isBooked) {
        String sql = "UPDATE seats SET isBooked = ? WHERE seat_id = ?";
        Object[] values = {isBooked ? 1 : 0, seatID};
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println("Seat status updated to booked.");
        } else {
            System.out.println("Failed to update seat status.");
        }
    }

    //-------------------------------------------------------------------------------------

    private void showTheaterAndScreenDetails(int movieID) {
        String sql = """
                    SELECT 
                        t.NAME AS Theater_Name,
                        s.SCREENID AS Screen_ID,
                        sh.SHOWID AS Show_ID,
                        sh.SHOWTIME AS Showtime
                    FROM 
                        showtime sh
                    JOIN 
                        theater t ON sh.THEATERID = t.THEATERID
                    JOIN 
                        screen s ON sh.SCREENID = s.SCREENID
                    WHERE 
                        sh.MOVIID = ?;
                """;

        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movieID);
            ResultSet rs = ps.executeQuery();

            // Displaying theater and showtimes information
            System.out.println("+-------------------------------------------------------+");
            System.out.println("| Theater Name   | Screen ID | Show ID | Showtime      |");
            System.out.println("+-------------------------------------------------------+");

            while (rs.next()) {
                String theaterName = rs.getString("Theater_Name");
                int screenID = rs.getInt("Screen_ID");
                int showID = rs.getInt("Show_ID");
                String showtime = rs.getString("Showtime");

                // Print the details for each showtime
                System.out.printf("| %-15s | %-9d | %-7d | %-15s |\n", theaterName, screenID, showID, showtime);
            }

            System.out.println("+-------------------------------------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------
    public void cancelTicket(int userID) {
        System.out.println("Tickets Booked at Different Showtimes:");

        // Fetch all bookings for the user and display in a table format
        String sql = "SELECT BookingID, SHOWID, SeatID, Status, CancelDate FROM booking WHERE UserID = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            // Displaying the table headers
            System.out.println("+------------+-------------+--------+--------------------+");
            System.out.println("| BookingID  | ShowtimeID  | SeatID | Status             | CancelDate        |");
            System.out.println("+------------+-------------+--------+--------------------+-------------------+");

            // Fetch and display the booking details
            while (rs.next()) {
                int bookingID = rs.getInt("BookingID");
                int showtimeID = rs.getInt("SHOWID");
                int seatID = rs.getInt("SeatID");
                String status = rs.getString("Status"); // Fetch status as a string
                String cancelDate = rs.getString("CancelDate");

                // Display booking information, handling the status as a string
                System.out.printf("| %-10d | %-11d | %-6d | %-18s | %-17s |\n", bookingID, showtimeID, seatID, status, cancelDate);
            }
            System.out.println("+------------+-------------+--------+--------------------+-------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.print("\nEnter BookingID to cancel booking: ");
        int bookingID_choice = sc.nextInt();
        sc.nextLine();  // Consume the remaining newline character

        // Asking for the cancellation reason
        System.out.println("Reason for cancelling:");
        String cancelReason = sc.nextLine();

        // SQL to cancel the booking
        String sqlUpdate = "UPDATE booking SET Status = 'CANCELLED', CancelDate = NOW(), CancelActionReason = ? WHERE BookingID = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
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
    //________________________________________________________________________________________________________
//    public void viewBookings(int userId) {
//        System.out.println("📅 3. View Your Ticket Bookings");
//        int userId = userId; // Set the userId to fetch bookings for the correct user
//
//        // SQL query to fetch bookings for the user from the booking table
//        String query = "SELECT * FROM booking WHERE UserID = " + userId;
//
//        try {
//            ResultSet rs = db.executeQuery(query);
//
//            // Check if the user has any bookings
//            if (rs.next()) {
//                System.out.println("\nYour Booked Tickets:\n");
//
//                // Print header of the table
//                System.out.printf("%-12s %-10s %-12s %-20s %-15s\n", "Booking ID", "Movie ID", "Theater ID", "Showtime", "Seats Booked");
//                System.out.println("--------------------------------------------------------------");
//
//                // Display each booking as a row in the table
//                do {
//                    int bookingId = rs.getInt("BookingID");
//                    int movieId = rs.getInt("MovieID");
//                    int theaterId = rs.getInt("TheaterID");
//                    String showtime = rs.getString("Showtime");
//                    int seatsBooked = rs.getInt("SeatsBooked");
//
//                    System.out.printf("%-12d %-10d %-12d %-20s %-15d\n", bookingId, movieId, theaterId, showtime, seatsBooked);
//                } while (rs.next()); // Iterate over all bookings
//            } else {
//                System.out.println("You don't have any bookings yet.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("An error occurred while fetching your bookings.");
//        }
//    }


}


