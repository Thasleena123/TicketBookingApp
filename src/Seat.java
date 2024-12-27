package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Seat {
    private DatabaseOperation db = new DatabaseOperation();
    private Scanner sc = new Scanner(System.in);


    public void insertSeats(int screenId, String level, int numSeats) {
        for (int seatNumber = 1; seatNumber <= numSeats; seatNumber++) {

            String sql = "INSERT INTO seats (screen_id, level, seat_number, isBooked, seat_status) VALUES (?, ?, ?, ?, ?)";
            Object[] values = {screenId, level, seatNumber, false, "available"};
            db.executeUpdate(sql, values);
        }
        System.out.println("Seats added successfully for level " + level + "!");
    }

    public void showSeatsInScreen(int screenId) {
        String sql = "SELECT * FROM seats WHERE screen_id = ?";
        ResultSet rs = db.getRecords(sql, screenId);
        try {
            while (rs.next()) {
                String level = rs.getString("level");
                int seatNumber = rs.getInt("seat_number");
                boolean isBooked = rs.getBoolean("isBooked");
                System.out.println("Level: " + level + ", Seat Number: " + seatNumber + ", Booked: " + isBooked);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) {
                    rs.close();  // Close the ResultSet
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSeatBooking(int screenId, int seatNumber, boolean isBooked, String seat_status) {

        String sql = "UPDATE seats SET isBooked = ?, seat_status = ? WHERE screen_id = ? AND seat_number = ?";


        Object[] values = {isBooked ? 1 : 0, seat_status, screenId, seatNumber};


        int rowsAffected = db.executeUpdate(sql, values);


        if (rowsAffected > 0) {
            if (isBooked) {
                System.out.println("Seat " + seatNumber + " booked successfully!");
            } else {
                System.out.println("Booking for seat " + seatNumber + " canceled successfully!");
            }
        } else {
            System.out.println("No seat found with the specified details.");
        }
    }


}
