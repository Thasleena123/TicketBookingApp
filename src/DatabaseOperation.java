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
            System.out.println("database connection failed " + e.getMessage());
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

    public void getAllBookingsForUser(int userID) {
        String sql = "SELECT * from booking where userid =?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("----- Booking Details -----");
                System.out.println("Booking ID: " + rs.getInt("BookingID"));
                System.out.println("Showtime ID: " + rs.getInt("ShowID"));
                System.out.println("Seat number: " + rs.getInt("seatID"));
        //        System.out.println("Status:" + rs.getInt("STATUS"));
//                System.out.println("CANCELDATE: " + rs.getDate("CANCELDATE"));
//                System.out.println("CANCELACTIONREASON:" + rs.getString("CANCELACTIONREASON"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int removeBooking(int bookingID) {

        String sql = "DELETE from booking where BookingID =?";
        int rowsAffected = 0;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public boolean isAdmin(int userID) {
        String sql = "SELECT isAdmin FROM user WHERE userID = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isAdmin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT userID, isAdmin FROM user WHERE username = ? AND password = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("userID");
                boolean isAdmin = rs.getBoolean("isAdmin");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUserID(String username) {
        // This method should fetch the userID based on the username from the database
        String sql = "SELECT userID FROM user WHERE username = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no user is found
    }

    public boolean registerUser(String username, String password, String phone) {
        String sql = "INSERT INTO user (username, password, phone, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, phone);
            ps.setBoolean(4, false);  // Default to regular user (not admin)
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Return true if the user was successfully registered
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if registration failed
    }
}



