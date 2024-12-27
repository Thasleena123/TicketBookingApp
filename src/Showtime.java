package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Showtime {
    DatabaseOperation db=new DatabaseOperation();
    public void insertShowtime(int moviId, int teaterId, int screenId, Timestamp showTime,int totalSeats,int availableSeats){
        String sql="insert into showtimes(moviId,teaterId,screenId,showTime,totalSeats,availableSeats)values(?,?,?,?,?,?,?)";
        Object[] values={moviId,teaterId,screenId,showTime,totalSeats,availableSeats};
        int rowsAffected = db.executeUpdate(sql, values);
        if(rowsAffected>0)
            System.out.println("Showtime added successfully");
        else
            System.out.println("Something went wrong.showTime not inserted.");

    }
    public void showShowtimes(){
        String sql = "select* from showtime";
        ResultSet rs = db.getRecords(sql);
        try {
            while (rs.next()) {
                System.out.println("showtime id:" + rs.getInt("showtimeID"));
                System.out.println("movi id:" + rs.getInt("moviID"));
                System.out.println("theater Id :" + rs.getString("theaterId"));
                System.out.println("showtime: " + rs.getTimestamp("showTime"));
                System.out.println("total seats:" + rs.getInt("totalSeats"));
                System.out.println("availableSeats:" + rs.getInt("availableSeats"));

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


