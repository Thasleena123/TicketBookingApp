package src;

import java.sql.*;

public class DatabaseOperation {
    static final String DB_URL = "jdbc:mysql://localhost:3306/movie_booking";
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
    public int getMovieIdByName(String movieName) {
        String sql = "SELECT moviID FROM movies WHERE title = ?";
        ResultSet rs = null;
        try (Connection conn = connectToDatabase(); // Assuming connectToDatabase() is defined
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movieName);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("moviID");
            } else {
                return -1; // Movie not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1; // Movie not found
    }

    public boolean isAdmin(int userID) {
        String sql = "SELECT isAdmin FROM user WHERE userid = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isAdmin");
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
        String sql = "INSERT INTO user (username, password, isAdmin, phone) VALUES (?, ?, ?, ?)";
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
    public boolean validateAdminLogin(String username, String password) {
        String sql = "SELECT password, isAdmin FROM user WHERE username = ?";
        try (Connection conn =connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                boolean isAdmin = rs.getBoolean("isAdmin");

                // Check if the password matches and if the user is an admin
                return password.equals(storedPassword) && isAdmin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn =connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isTheaterIdValid(int theaterId) {
        String sql = "SELECT 1 FROM theater WHERE theaterId = ?";

        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, theaterId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Returns true if the theater ID exists

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false if an exception occurs
    }

}
