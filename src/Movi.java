package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Movi {
    private String title;
    private String genre;
    private int duration;
    private float rating;


    DatabaseOperation db = new DatabaseOperation();

    public void insertMovi(String title, String genre, int duration, float rating) {
        String sql = "insert into movies(title,genre,duration,rating)Values(?,?,?,?)";
        Object[] values = {title, genre, duration, rating};
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println("movies inserted successfully");
        } else {
            System.out.println("something went wrong.movi not inserted");
        }
    }

    public void showMovies() {
        String sql = "SELECT * FROM movies";
        ResultSet rs = db.getRecords(sql);

        System.out.println("Available Movies:");

        try {
            if (!rs.isBeforeFirst()) { // Check if there are any results
                System.out.println("No movies found.");
                return;
            }

            // Print table header
            System.out.println("+--------+----------------------+----------+----------+--------+");
            System.out.printf("| %-6s | %-20s | %-8s | %-8s | %-6s |\n",
                    "ID", "Title", "Genre", "Duration", "Rating");
            System.out.println("+--------+----------------------+----------+----------+--------+");

            // Print table rows
            while (rs.next()) {
                int movieId = rs.getInt("moviID");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");
                float rating = rs.getFloat("rating");

                System.out.printf("| %-6d | %-20s | %-8s | %-8d | %-6.1f |\n",
                        movieId, title, genre, duration, rating);
            }

            // Print table footer
            System.out.println("+--------+----------------------+----------+----------+--------+");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close(); // Close the ResultSet
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


//    public void showMovies() {
//        String sql = "select* from movies";
//        ResultSet rs = db.getRecords(sql);
//        try {
//            while (rs.next()) {
//                System.out.println("movi id:" + rs.getInt("moviID"));
//                System.out.println("title :" + rs.getString("title"));
//                System.out.println("genre :" + rs.getString("genre"));
//                System.out.println("duration:" + rs.getInt("duration"));
//                System.out.println("rating:" + rs.getFloat("rating"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}

    private void showAvailableShowtimes(int movieId) {
        String sql = "SELECT SHOWID, MOVIID, THEATERID, showtime, AVAILABLE_SEATS " +
                "FROM showtime WHERE MOVIID = ?";
        ResultSet rs = db.getRecords(sql, movieId);
        try {
            if (!rs.isBeforeFirst()) { // Check if there are any showtimes for the movie
                System.out.println("No showtimes available for this movie.");
                return;
            }

            System.out.println("Available Showtimes for the selected movie:");
            System.out.println("+--------+-------------------+----------+----------+------------------+");
            System.out.printf("| %-6s | %-17s | %-8s | %-8s | %-16s |\n",
                    "Show ID", "Showtime", "Theater", "Seats", "Available Seats");
            System.out.println("+--------+-------------------+----------+----------+------------------+");

            // Print showtimes and available seats
            while (rs.next()) {
                int showId = rs.getInt("SHOWID");
                String showtime = rs.getString("showtime");
                int availableSeats = rs.getInt("AVAILABLE_SEATS");

                System.out.printf("| %-6d | %-17s | %-8d | %-8d | %-16d |\n",
                        showId, showtime, rs.getInt("THEATERID"), availableSeats, availableSeats);
            }

            System.out.println("+--------+-------------------+----------+----------+------------------+");

            // Step to let the user select a showtime
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Showtime ID you want to book:");
            int selectedShowtimeId = sc.nextInt();

            showAvailableSeatsForShowtime(selectedShowtimeId);
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

    // Method to show available seats for the selected showtime
    private void showAvailableSeatsForShowtime(int showtimeId) {
        String sql = "SELECT seat_id, level, seat_number FROM seats WHERE showtime_id = ? AND isBooked = false";
        ResultSet rs = db.getRecords(sql, showtimeId);

        try {
            if (!rs.isBeforeFirst()) { // Check if there are any available seats
                System.out.println("No available seats for this showtime.");
                return;
            }

            System.out.println("Available Seats for the selected showtime:");
            System.out.println("+---------+-------+-------------+");
            System.out.printf("| Seat ID | Level | Seat Number |\n");
            System.out.println("+---------+-------+-------------+");

            while (rs.next()) {
                int seatId = rs.getInt("seat_id");
                String level = rs.getString("level");
                int seatNumber = rs.getInt("seat_number");
                System.out.printf("| %-7d | %-5s | %-11d |\n", seatId, level, seatNumber);
            }

            System.out.println("+---------+-------+-------------+");


            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Seat ID to book:");
            int selectedSeatId = sc.nextInt();

            updateSeatBookingForShowtime(showtimeId, selectedSeatId);
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

    // Method to update the seat booking for the selected showtime
    private void updateSeatBookingForShowtime(int showtimeId, int seatId) {
        String sql = "UPDATE seats SET isBooked = ?, seat_status = ? WHERE seat_id = ? AND showtime_id = ?";
        Object[] values = {true, "booked", seatId, showtimeId};

        int rowsAffected = db.executeUpdate(sql, values);

        if (rowsAffected > 0) {
            System.out.println("Seat booked successfully!");
        } else {
            System.out.println("Failed to book seat. Please try again.");
        }
    }
}

