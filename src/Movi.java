package src;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Movi {
    private String title;
    private String genre;
    private int duration;
    private float rating;

    public Movi(String title, String genre, int duration, float rating) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.db = db;
        this.rating = rating;
    }

    DatabaseOperation db = new DatabaseOperation();

    public void insertMovi() {
        String sql = "insert into movies(title,genre,duration,rating)Values(?,?,?,?)";
        Object[] values = {title, genre, duration, rating};
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0) {
            System.out.println("movies inserted successfully");
        } else {
            System.out.println("something went wrong.movi not inserted");
        }
    }


    public void showMovies() {
        String sql = "select* from movies";
        ResultSet rs = db.getRecords(sql);
        try {
            while (rs.next()) {
                System.out.println("movi id:" + rs.getInt("moviID"));
                System.out.println("title :" + rs.getString("title"));
                System.out.println("genre :" + rs.getString("genre"));
                System.out.println("duration:" + rs.getInt("duration"));
                System.out.println("rating:" + rs.getFloat("rating"));
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



