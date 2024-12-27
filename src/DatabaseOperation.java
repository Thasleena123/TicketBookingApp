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
            System.out.println(e);
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
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

}

