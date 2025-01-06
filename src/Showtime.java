package src;

import java.sql.*;

public class Showtime {
    DatabaseOperation db=new DatabaseOperation();
//    public void insertShowtime(int MOVIID, int THEATERID, int SCREENID, Timestamp start_time, Timestamp end_time) {
//        // SQL query to check if the showtime overlaps with any other movie
//        String checkSql = "SELECT COUNT(*) FROM showtime WHERE THEATERID = ? AND SCREENID = ? " +
//                "AND ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?) OR " +
//                "(? BETWEEN start_time AND end_time) OR (? BETWEEN start_time AND end_time))";
//
//        // SQL query to insert the new showtime
//        String insertSql = "INSERT INTO showtime(MOVIID, THEATERID, SCREENID, start_time, end_time) VALUES(?, ?, ?, ?, ?)";
//
//        try (Connection conn = db.connectToDatabase();
//             PreparedStatement checkPs = conn.prepareStatement(checkSql);
//             PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
//
//            // Set parameters for the check query
//            checkPs.setInt(1, THEATERID);
//            checkPs.setInt(2, SCREENID);
//            checkPs.setTimestamp(3, start_time);  // Check if the new start date overlaps
//            checkPs.setTimestamp(4, end_time);    // Check if the new end date overlaps
//            checkPs.setTimestamp(5, start_time);  // Check if the new start date falls within existing showtimes
//            checkPs.setTimestamp(6, end_time);    // Check if the new end date falls within existing showtimes
//            checkPs.setTimestamp(7, start_time);  // Check if the new showtime is within the range of any existing showtime
//            checkPs.setTimestamp(8, end_time);    // Check if the new showtime is within the range of any existing showtime
//
//            // Execute the check query
//            ResultSet rs = checkPs.executeQuery();
//            rs.next(); // Move to the first row
//            int count = rs.getInt(1); // Get the count of overlapping showtimes
//
//            if (count > 0) {
//                // If there is an overlap, do not insert the new showtime
//                System.out.println("Showtime overlaps with another movie in the same theater and screen.");
//            } else {
//                // If no overlap, insert the new showtime
//                insertPs.setInt(1, MOVIID);
//                insertPs.setInt(2, THEATERID);
//                insertPs.setInt(3, SCREENID);
//                insertPs.setTimestamp(4, start_time);
//                insertPs.setTimestamp(5, end_time);
//
//                int rowsAffected = insertPs.executeUpdate();
//
//                if (rowsAffected > 0) {
//                    System.out.println("Showtime inserted successfully.");
//                } else {
//                    System.out.println("Failed to insert showtime.");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
public void insertShowtime(int MOVIID, int THEATERID, int SCREENID, Timestamp start_time, Timestamp end_time) {
    // SQL query to check if the showtime overlaps with any other movie
    String checkSql = "SELECT COUNT(*) FROM showtime WHERE THEATERID = ? AND SCREENID = ? " +
            "AND ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?) OR " +
            "(? BETWEEN start_time AND end_time) OR (? BETWEEN start_time AND end_time))";

    // SQL query to insert the new showtime
    String insertSql = "INSERT INTO showtime(MOVIID, THEATERID, SCREENID, start_time, end_time) VALUES(?, ?, ?, ?, ?)";

    try (Connection conn = db.connectToDatabase();
         PreparedStatement checkPs = conn.prepareStatement(checkSql);
         PreparedStatement insertPs = conn.prepareStatement(insertSql)) {

        // Set parameters for the check query
        checkPs.setInt(1, THEATERID);
        checkPs.setInt(2, SCREENID);
        checkPs.setTimestamp(3, start_time);  // Check if the new start date overlaps
        checkPs.setTimestamp(4, end_time);    // Check if the new end date overlaps
        checkPs.setTimestamp(5, start_time);  // Check if the new start date falls within existing showtimes
        checkPs.setTimestamp(6, end_time);    // Check if the new end date falls within existing showtimes
        checkPs.setTimestamp(7, start_time);  // Check if the new showtime is within the range of any existing showtime
        checkPs.setTimestamp(8, end_time);    // Check if the new showtime is within the range of any existing showtime

        // Execute the check query
        ResultSet rs = checkPs.executeQuery();
        rs.next(); // Move to the first row
        int count = rs.getInt(1); // Get the count of overlapping showtimes

        if (count > 0) {
            // If there is an overlap, do not insert the new showtime
            System.out.println("Showtime overlaps with another movie in the same theater and screen.");
        } else {
            // If no overlap, insert the new showtime
            insertPs.setInt(1, MOVIID);
            insertPs.setInt(2, THEATERID);
            insertPs.setInt(3, SCREENID);
            insertPs.setTimestamp(4, start_time);
            insertPs.setTimestamp(5, end_time);

            int rowsAffected = insertPs.executeUpdate();

            // Debugging: Log the inserted values to check
            System.out.println("Inserting Showtime with:");
            System.out.println("MOVIID: " + MOVIID);
            System.out.println("THEATERID: " + THEATERID);
            System.out.println("SCREENID: " + SCREENID);
            System.out.println("Start Time: " + start_time);
            System.out.println("End Time: " + end_time);

            if (rowsAffected > 0) {
                System.out.println("Showtime inserted successfully.");
            } else {
                System.out.println("Failed to insert showtime.");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

//    public void showShowtimes() {
//        String sql = "SELECT s.SHOWID, m.TITLE, t.NAME, s.start_time, s.end_time" +
//                "FROM showtime s " +
//                "JOIN movies m ON s.MOVIID = m.MOVIID " +
//                "JOIN theater t ON s.THEATERID = t.THEATERID";
//
//        ResultSet rs = db.getRecords(sql); // Get the ResultSet
//
//        try {
//            if (rs == null) {
//                System.out.println("No showtimes found.");
//                return;
//            }
//
//            // Print Table Header
//            System.out.println("+------------+---------------------+------------------+---------------------+---------------------+----------------+");
//            System.out.printf("| %-10s | %-18s | %-16s | %-19s | %-19s | %-14s |\n", "Showtime ID", "Movie", "Theater", "Start Time", "End Time");
//            System.out.println("+------------+---------------------+------------------+---------------------+---------------------+----------------+");
//
//            // Print Table Rows
//            while (rs.next()) {
//                int showId = rs.getInt("SHOWID");
//                String movieTitle = rs.getString("TITLE");
//                String theaterName = rs.getString("NAME");
//                Timestamp startTime = rs.getTimestamp("start_time");
//                Timestamp endTime = rs.getTimestamp("end_time");
//
//
//                // Print each row with proper formatting
//                System.out.printf("| %-10d | %-18s | %-16s | %-19s | %-19s | %-14d |\n",
//                        showId, movieTitle, theaterName, startTime, endTime);
//            }
//
//            // Table Footer
//            System.out.println("+------------+---------------------+------------------+---------------------+---------------------+----------------+");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void showShowtimes() {
        String sql = "SELECT s.SHOWID, m.TITLE, t.NAME, s.start_time, s.end_time " +
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
            System.out.println("+------------+---------------------+------------------+---------------------+---------------------+");
            System.out.printf("| %-10s | %-18s | %-16s | %-19s | %-19s |\n", "Showtime ID", "Movie", "Theater", "Start Time", "End Time");
            System.out.println("+------------+---------------------+------------------+---------------------+---------------------+");

            // Print Table Rows
            while (rs.next()) {
                int showId = rs.getInt("SHOWID");
                String movieTitle = rs.getString("TITLE");
                String theaterName = rs.getString("NAME");
                Timestamp startTime = rs.getTimestamp("start_time");
                Timestamp endTime = rs.getTimestamp("end_time");

                // Print each row with proper formatting
                System.out.printf("| %-10d | %-18s | %-16s | %-19s | %-19s |\n", showId, movieTitle, theaterName, startTime, endTime);
            }

            // Table Footer
            System.out.println("+------------+---------------------+------------------+---------------------+---------------------+");

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










