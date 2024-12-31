package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Showtime {
    DatabaseOperation db=new DatabaseOperation();
    public void insertShowtime(int MOVIID, int THEATERID, int SCREENID, Timestamp showtime,int AVAILABLE_SEATS){
        String sql="insert into showtime(MOVIID,THEATERID,SCREENID,showtime,TOTAL_SEATS,AVAILABLE_SEATS)values(?,?,?,?,?,?)";
        Object[] values={MOVIID,THEATERID,SCREENID,showtime,AVAILABLE_SEATS};
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
                System.out.println("showtime id:" + rs.getInt("SHOWID"));
                System.out.println("movi id:" + rs.getInt("MOVIID"));
                System.out.println("Scrren id:"+ rs.getInt("screenId"));
                System.out.println("theater Id :" + rs.getString("THEATERID"));
                System.out.println("showtime: " + rs.getTimestamp("showtime"));

                System.out.println("availableSeats:" + rs.getInt("AVAILABLE_SEATS"));

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


