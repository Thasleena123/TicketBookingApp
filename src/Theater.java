package src;

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

    public void showTheateres() {
        System.out.println("Calling showTheateres method...");
        String sql = "select* from theater";
        ResultSet rs = db.getRecords(sql);
        try {
            while (rs.next()) {
                System.out.println("THEATERID:" + rs.getInt("THEATERID"));
                System.out.println("NAME :" + rs.getString("NAME"));
                System.out.println("LOCATION:" + rs.getString("LOCATION"));

            }
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
}





