package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Screen {
    private DatabaseOperation db = new DatabaseOperation();
    private Scanner sc = new Scanner(System.in);


    public void insertScreen(int theaterId, String screenNumber, int totalSeats) {
        String sql = "INSERT INTO screen (theaterId,  screenNumber, totalSeats ) VALUES (?, ?, ?)";
        Object[] values = {theaterId, screenNumber, totalSeats};
        db.executeUpdate(sql, values);
        System.out.println("Screen added successfully!");
    }

    public void showScreensInTheater(int theaterId) {
        String sql = "SELECT * FROM screen WHERE theaterId = ?";
        ResultSet rs = null;

        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, theaterId);
            rs = ps.executeQuery();

            if (rs != null) {

                while (rs.next()) {
                    System.out.println("theaterId: " + rs.getInt("theaterId"));
                    System.out.println("Screen Number: " + rs.getInt("screenNumber"));
                    System.out.println("TotalSeats available in the screen: " + rs.getInt("totalSeats"));
                }
            } else {
                System.out.println("No screens found for the specified theater.");
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
    }
}
