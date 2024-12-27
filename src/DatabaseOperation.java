package src;

import java.sql.*;

public class DatabaseOperation {
    static final String DB_URL = "jdbc:mysql://localhost/movie_booking";
    static final String USER = "root";
    static final String PASS = "password";

    public Connection connectToDatabase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("database connection failed "+e.getMessage());
        }
        return conn;
    }


    public int executeUpdate(String sql, Object[] values) {
        int rowAffected = 0;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rowAffected = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowAffected;
    }

    public ResultSet getRecords(String sql) {
        ResultSet rs = null;
        try {
            Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
//    public ResultSet getRecords(String sql, Object... params) {
//        ResultSet rs = null;
//        try (Connection conn = connectToDatabase();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            for (int i = 0; i < params.length; i++) {
//                ps.setObject(i + 1, params[i]);  // Set each parameter in the prepared statement
//            }
//            rs = ps.executeQuery();
//            return rs;
//
//        } catch (SQLException e) {
//            System.out.println("Error getting records: " + e.getMessage());
//        }
//        return null;
//    }

    public ResultSet getRecords(String sql, Object... params) {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectToDatabase();
            ps = conn.prepareStatement(sql);


            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }


            rs = ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error getting records: " + e.getMessage());
        }


        return rs;
    }

    public int getAvailableSeats(String sql, int showtimeID) {
        int availableSeats = 0;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeID);
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableSeats;
    }

}


