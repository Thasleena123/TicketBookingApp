package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Theater {
    private String name;
    private String location;

    private DatabaseOperation db = new DatabaseOperation();

    public void insertTheater(String name, String location) {
        this.name = name;
        this.location = location;

        String sql = "insert into theater(name,location) values(?,?)";
        Object[] values = {name, location};
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println("theater inserted successfully");
        } else {
            System.out.println("something went wrong");
        }
    }

//    public void showTheateres() {
//        System.out.println("Calling showTheateres method...");
//        String sql = "select* from theater";
//        ResultSet rs = db.getRecords(sql);
//        try {
//            while (rs.next()) {
//                System.out.println("THEATERID:" + rs.getInt("THEATERID"));
//                System.out.println("NAME :" + rs.getString("NAME"));
//                System.out.println("LOCATION:" + rs.getString("LOCATION"));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) rs.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
   // }
public void showTheateres() {
    System.out.println("Calling showTheateres method...");
    String sql = "SELECT * FROM theater";
    ResultSet rs = db.getRecords(sql);

    try {
        if (!rs.isBeforeFirst()) { // Check if there are any results
            System.out.println("No theaters found.");
            return;
        }

        // Print table header
        System.out.println("+-----------+----------------------+----------------------+");
        System.out.printf("| %-9s | %-20s | %-20s |\n", "THEATER ID", "NAME", "LOCATION");
        System.out.println("+-----------+----------------------+----------------------+");

        // Print table rows
        while (rs.next()) {
            int theaterId = rs.getInt("THEATERID");
            String name = rs.getString("NAME");
            String location = rs.getString("LOCATION");

            System.out.printf("| %-9d | %-20s | %-20s |\n", theaterId, name, location);
        }

        // Print table footer
        System.out.println("+-----------+----------------------+----------------------+");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



    public void removeTheater(int theaterId) {
    try (Connection conn = db.connectToDatabase()) {
        // Step 1: Delete dependent records in the 'seats' table first
        String deleteSeatsSQL = "DELETE FROM seats WHERE showtime_id IN (SELECT SHOWID FROM showtime WHERE THEATERID = ?)";
        try (PreparedStatement ps1 = conn.prepareStatement(deleteSeatsSQL)) {
            ps1.setInt(1, theaterId);
            ps1.executeUpdate();
        }

        // Step 2: Delete dependent records in the 'showtime' table
        String deleteShowtimesSQL = "DELETE FROM showtime WHERE THEATERID = ?";
        try (PreparedStatement ps2 = conn.prepareStatement(deleteShowtimesSQL)) {
            ps2.setInt(1, theaterId);
            ps2.executeUpdate();
        }

        // Step 3: Now delete the theater
        String deleteTheaterSQL = "DELETE FROM theater WHERE THEATERID = ?";
        try (PreparedStatement ps3 = conn.prepareStatement(deleteTheaterSQL)) {
            ps3.setInt(1, theaterId);
            int rowsAffected = ps3.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Theater removed successfully.");
            } else {
                System.out.println("Failed to delete theater. Theater ID might be invalid.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}







