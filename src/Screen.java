package src;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Screen {
    private DatabaseOperation db = new DatabaseOperation();
    private Scanner sc = new Scanner(System.in);


    public void insertScreen(int theaterId, int screenNumber, int totalSeats) {
        String sql = "INSERT INTO screen (theaterId,  screenNumber, totalSeats ) VALUES (?, ?, ?)";
        Object[] values = {theaterId, screenNumber, totalSeats};
        db.executeUpdate(sql, values);
        //System.out.println("Screen added successfully!");
    }


    public void showScreensInTheater(int theaterId) {
        String sql = "SELECT * FROM screen WHERE theaterId = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, theaterId);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) { // Check if there are any results
                System.out.println("No screens found for the specified theater.");
                return;
            }

            // Print table header
            System.out.println("+----------------+----------------+----------------+");
            System.out.printf("| %-14s |  %-14s |%-14s |%-14s |\n", "Theater ID", "Screen ID", "Screen Number", "Total Seats");
            System.out.println("+----------------+----------------+----------------+");


            while (rs.next()) {
                int theaterIdFromDB = rs.getInt("theaterId");
                int screenID = rs.getInt("SCREENID");
                int screenNumber = rs.getInt("screenNumber");
                int totalSeats = rs.getInt("totalSeats");

                System.out.printf("| %-14d | %-14d | %-14d | %-14d |\n", theaterIdFromDB, screenID, screenNumber, totalSeats);
            }


            System.out.println("+----------------+----------------+----------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void showMoviesOnScreens() {
        String sql = "SELECT m.MOVIID, m.TITLE, s.SCREENID, s.SCREENNUMBER, st.start_time "
                + "FROM showtime st "
                + "JOIN movies m ON st.MOVIID = m.MOVIID "
                + "JOIN screen s ON st.SCREENID = s.SCREENID";

        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) { // Check if there are any results
                System.out.println("No movies scheduled on screens.");
                return;
            }

            // Print table header
            System.out.println("+----------+--------------------+----------+----------------+-----------------------+");
            System.out.printf("| %-8s | %-18s | %-8s | %-14s | %-21s |\n", "Movie ID", "Movie Title", "Screen ID", "Screen Number", "Showtime");
            System.out.println("+----------+--------------------+----------+----------------+-----------------------+");

            // Print table rows
            while (rs.next()) {
                int movieId = rs.getInt("MOVIID");
                String movieTitle = rs.getString("TITLE");
                int screenId = rs.getInt("SCREENID");
                int screenNumber = rs.getInt("SCREENNUMBER");


                String showtime = rs.getTimestamp("start_time") != null
                        ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("start_time"))
                        : "N/A";

                System.out.printf("| %-8d | %-18s | %-8d | %-14d | %-21s |\n", movieId, movieTitle, screenId, screenNumber, showtime);
            }

            System.out.println("+----------+--------------------+----------+----------------+-----------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeMovieFromScreen(int movie_id, int screen_id) {
        String sql = "DELETE FROM showtime WHERE MOVIID = ? AND SCREENID = ?";

        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, movie_id);
            ps.setInt(2, screen_id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Movie removed from screen successfully.");
            } else {
                System.out.println("Failed to remove movie from screen. Movie or Screen ID might be invalid.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addShowToScreen(int screenId) {

        int showCount = getShowCountForScreen(screenId);


        if (showCount < 4) {
            int showsToAdd = 4 - showCount;
            System.out.println("You need to add " + showsToAdd + " more show(s) to meet the minimum requirement of 4 shows.");
            for (int i = 0; i < showsToAdd; i++) {
                addShow(screenId);
            }
        } else {
            System.out.println("Screen already has 4 or more shows. Would you like to add more? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                addShow(screenId);
            }
        }
    }


    public int getShowCountForScreen(int screenId) {
        String sql = "SELECT COUNT(*) FROM showtime WHERE SCREENID = ?";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, screenId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void addShow(int screenId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Movie ID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter Show Date and Time (YYYY-MM-DD HH:MM:SS): ");
        scanner.nextLine();
        String showTime = scanner.nextLine().trim();
        System.out.println("DEBUG: Received show time input: '" + showTime + "'");

        if (!isValidDateFormat(showTime)) {
            System.out.println("Error: Invalid date/time format. Please use the format 'YYYY-MM-DD HH:MM:SS'.");
            return;
        }


        if (!isFutureDate(showTime)) {
            System.out.println("Error: Show time must be in the future.");
            return;
        }

        // Insert show into the database
        String sql = "INSERT INTO showtime (MOVIID, SCREENID, showtime) VALUES (?, ?, ?)";
        try (Connection conn = db.connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ps.setInt(2, screenId);
            ps.setString(3, showTime);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Show added successfully.");
            } else {
                System.out.println("Failed to add show.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidDateFormat(String showTime) {

        String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
        if (!showTime.matches(regex)) {
            System.out.println("Error: The show time is missing the time part. Please ensure you enter the full format.");
        }
        return showTime.matches(regex);
    }


    // Method to check if the entered date/time is in the future
    public boolean isFutureDate(String showTime) {
        try {
            // parse to timestampobject
            Timestamp enteredTimestamp = Timestamp.valueOf(showTime);
            //current
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            if (enteredTimestamp.after(currentTimestamp)) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid date/time format.");
            return false;
        }
    }

}



