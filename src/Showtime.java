package src;

import java.sql.*;

public class Showtime {
    DatabaseOperation db=new DatabaseOperation();
//     public void insertShowtime(int MOVIID, int THEATERID, int SCREENID, Timestamp showtime,int AVAILABLE_SEATS){
//        String sql="insert into showtime(MOVIID,THEATERID,SCREENID,showtime,AVAILABLE_SEATS)values(?,?,?,?,?)";
//        Object[] values={MOVIID,THEATERID,SCREENID,showtime,AVAILABLE_SEATS};
//        int rowsAffected = db.executeUpdate(sql, values);
//        if(rowsAffected>0)
//            System.out.println("Showtime added successfully");
//        else
//            System.out.println("Something went wrong.showTime not inserted.");
//
//    }
public int insertShowtime(int MOVIID, int THEATERID, int SCREENID, Timestamp showtime, int AVAILABLE_SEATS) {
    String sql = "INSERT INTO showtime(MOVIID, THEATERID, SCREENID, showtime, AVAILABLE_SEATS) VALUES(?, ?, ?, ?, ?)";
    Object[] values = {MOVIID, THEATERID, SCREENID, showtime, AVAILABLE_SEATS};

    int showtimeId = -1;  // Variable to store the generated Showtime ID

    try (Connection conn = db.connectToDatabase();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        // Set the parameters for the SQL query
        ps.setInt(1, MOVIID);
        ps.setInt(2, THEATERID);
        ps.setInt(3, SCREENID);
        ps.setTimestamp(4, showtime);
        ps.setInt(5, AVAILABLE_SEATS);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            // Retrieve the generated showtime_id
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                showtimeId = rs.getInt(1);  // Get the first generated key
                System.out.println("Showtime added successfully with Showtime ID: " + showtimeId);
            }
        } else {
            System.out.println("Something went wrong. Showtime not inserted.");
        }
    } catch (SQLException e) {
        System.out.println("Error inserting showtime: " + e.getMessage());
    }

    return showtimeId;  // Return the generated Showtime ID
}

//
//public void showShowtimes() {
//    String sql = "SELECT * FROM showtime";
//    ResultSet rs = null;
//    try {
//        rs = db.getRecords(sql);  // Get the ResultSet
//
//        if (rs == null || !rs.next()) {
//            System.out.println("No showtimes found.");
//            return;
//        }
//
//        System.out.println("----- Available Showtimes -----");
//        do {
//            System.out.println("Showtime ID: " + rs.getInt("SHOWID"));
//            System.out.println("Movie ID: " + rs.getInt("MOVIID"));
//            System.out.println("Screen ID: " + rs.getInt("screenId"));
//            System.out.println("Theater ID: " + rs.getString("THEATERID"));
//            System.out.println("Showtime: " + rs.getTimestamp("showtime"));
//            System.out.println("Available Seats: " + rs.getInt("AVAILABLE_SEATS"));
//            System.out.println("--------------------------------");
//        } while (rs.next());  // Loop through the result set
//    } catch (SQLException e) {
//        System.out.println("Error fetching showtimes: " + e.getMessage());
//    } finally {
//        try {
//            if (rs != null) {
//                rs.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



//    public void showShowtimes() {
//        String sql = "SELECT s.SHOWID, m.TITLE, t.NAME, s.showtime, s.AVAILABLE_SEATS " +
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
//            System.out.println("----- Available Showtimes -----");
//            while (rs.next()) {
//                System.out.println("Showtime ID: " + rs.getInt("SHOWID"));
//                System.out.println("Movie: " + rs.getString("TITLE"));
//                System.out.println("Theater: " + rs.getString("NAME"));
//                System.out.println("Showtime: " + rs.getTimestamp("showtime"));
//                System.out.println("Available Seats: " + rs.getInt("AVAILABLE_SEATS"));
//                System.out.println("--------------------------------");
//            }

//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Close the ResultSet when done
//            try {
//                if (rs != null) rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

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
            // Close the ResultSet when done
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    }










