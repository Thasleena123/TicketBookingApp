package src;

import java.sql.*;

public class Showtime {
    DatabaseOperation db=new DatabaseOperation();
    public int insertShowtime(int MOVIID, int THEATERID, int SCREENID, Timestamp showtime, Timestamp startDate, Timestamp endDate) {
        // SQL query to insert a showtime along with start_date and end_date
        String sql = "INSERT INTO showtime(MOVIID, THEATERID, SCREENID, showtime, start_date, end_date) VALUES(?, ?, ?, ?, ?, ?)";

        int showtimeId = -1;  // Variable to store the generated Showtime ID

        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the SQL query
            ps.setInt(1, MOVIID);
            ps.setInt(2, THEATERID);
            ps.setInt(3, SCREENID);
            ps.setTimestamp(4, showtime);
            ps.setTimestamp(5, startDate);  // Set start date
            ps.setTimestamp(6, endDate);    // Set end date

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated showtime_id
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    showtimeId = rs.getInt(1);  ;
                }
            } else {
                System.out.println("Something went wrong. Showtime not inserted.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting showtime: " + e.getMessage());
        }

        return showtimeId;  // Return the generated Showtime ID
    }


    public void showShowtimes() {
        String sql = "SELECT s.SHOWID, m.TITLE, t.NAME, s.showtime, s.AVAILABLE_SEATS " +
                "FROM showtime s " +
                "JOIN movies m ON s.MOVIID = m.MOVIID " +
                "JOIN theater t ON s.THEATERID = t.THEATERID";

        ResultSet rs = db.getRecords(sql); // Get the ResultSet

        try {
            if (rs == null) {
                System.out.println("No showtimes found.");
                return;
            }

            // Print Table Header
            System.out.println("+------------+---------------------+------------------+---------------------+----------------+");
            System.out.printf("| %-10s | %-18s | %-16s | %-19s | %-14s |\n", "Showtime ID", "Movie", "Theater", "Showtime", "Available Seats");
            System.out.println("+------------+---------------------+------------------+---------------------+----------------+");

            // Print Table Rows
            while (rs.next()) {
                int showId = rs.getInt("SHOWID");
                String movieTitle = rs.getString("TITLE");
                String theaterName = rs.getString("NAME");
                Timestamp showtime = rs.getTimestamp("showtime");
                int availableSeats = rs.getInt("AVAILABLE_SEATS");

                // Print each row with proper formatting
                System.out.printf("| %-10d | %-18s | %-16s | %-19s | %-14d |\n", showId, movieTitle, theaterName, showtime, availableSeats);
            }

            // Table Footer
            System.out.println("+------------+---------------------+------------------+---------------------+----------------+");

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

    }










