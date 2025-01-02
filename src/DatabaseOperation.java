package src;

import java.sql.*;

public class DatabaseOperation {
    static final String DB_URL = "jdbc:mysql://localhost:3306/movi_booking";
    static final String USER = "root";
    static final String PASS = "password";

    public Connection connectToDatabase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
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
            System.out.println("Error executing update: " + e.getMessage());
        }
        return rowAffected;
    }
    public ResultSet getRecords(String sql, Object... params) {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectToDatabase();  // Don't close the connection here
            ps = conn.prepareStatement(sql);

            // Set parameters for the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            // Execute query and store the ResultSet
            rs = ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error getting records: " + e.getMessage());
        }
        return rs; // Return the ResultSet
    }


    public boolean isAdmin(int userID) {
        String sql = "SELECT isadmin FROM user WHERE userid = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isadmin");
            }
        } catch (SQLException e) {
            System.out.println("Error checking admin status: " + e.getMessage());
        }
        return false;
    }

    public int getUserID(String username) {

        String sql = "SELECT userid FROM user WHERE username = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("userid");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user ID: " + e.getMessage());
        }
        return -1;
    }



    public boolean registerUser(String username, String password, String phone, boolean isAdmin) {
        String sql = "INSERT INTO user (username, password, isadmin, phoneno) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setBoolean(3, isAdmin);
            ps.setString(4, phone);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
        return false;
    }

    public String validatePass(String sql, String username) {
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Error validating password: " + e.getMessage());
        }
        return null;
    }

    public int removeBooking(int bookingID) {
        String sql = "DELETE FROM booking WHERE bookingid = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error removing booking: " + e.getMessage());
        }
        return 0;
    }

    public void getAllBookingsForUser(int userID) {
        String sql = "SELECT * FROM booking WHERE userid = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("----- Booking Details -----");
                System.out.println("Booking ID: " + rs.getInt("bookingid"));
                System.out.println("Showtime ID: " + rs.getInt("showid"));
                System.out.println("Seat number: " + rs.getInt("seatid"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
    }
}
