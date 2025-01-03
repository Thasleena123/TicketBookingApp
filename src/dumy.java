package src;

public class dumy {
    //package src;
//
//import java.util.Scanner;
//
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//
//public class Admin extends User{
//    Scanner sc = new Scanner(System.in);
//    int choice;
//    int UserID;
//    String title;
//    String genre;
//    float rating;
//    int duration;
//    int theater_id;
//    int showtime_hour;
//    int showtime_min;
//    Timestamp showtime;
//    String level;
//    int movieid;
//    int screen_id;
//    int screenNumber;
//    int total_seats;
//    int available_seats;
//    String adress;
//    String theater_name;
//    int totalSeats;
//    Movie m = new Movie();
//    Theater t = new Theater();
//    Showtime st = new Showtime();
//    DatabaseOperation db = new DatabaseOperation();
//    Screen scr = new Screen();
//    Seat se = new Seat();
//
//    public int takeUserId() {
//        System.out.println("enter user id");
//        return sc.nextInt();
//    }
//
//
//        public void adminMenu(int userID) {
//            // Check if the user is an admin
//            if (!db.isAdmin(userID)) {
//                System.out.println("You are not authorized to access the admin menu.");
//                return;
//            }
//
//
//
//
//
//        while (true) {
//
//            System.out.println("-----ADMIN MENU-------");
//            System.out.println("PRESS 1 TO ADD MOVIES");
//            System.out.println("PRESS 2 TO SEE ALL MOVIES");
//            System.out.println("PRESS 3 TO PUT A MOVI FOR A SHOW TIME");
//            System.out.println("PRESS 4 TO SEE ALL SHOWTIME");
//            System.out.println("PRESS 5 TO ADD THEATERS");
//            System.out.println("PRESS 6 TO SEE ALL THEATERS");
//            System.out.println("PRESS 7 TO ADD SCREEN TO THEATERS");
//            System.out.println("PRESS 8 TO SEE SCREENS IN THE THEATERS");
//            System.out.println("PRESS 9 TO ADD SEATS TO SCREENS");
//            System.out.println("PRESS 10 TO SEE SEATS IN THE SCREEN ");
//            System.out.println("PRESS 11 TO VIEW MOVIES ON SCREENS");
//            System.out.println("PRESS 12 TO REMOVE MOVIES FROM SCREEN");
//            System.out.println("PRESS 13 TO REMOVE THEATER");
//            System.out.println("PRESS 14 TO EXIT");
//            System.out.println("ENTER YOUR CHOICE");
//            choice = sc.nextInt();
//            switch (choice) {
//                case 1:
//                    // add movies
//                    System.out.println("enter a movi title");
//                    sc.nextLine();
//                    title = sc.nextLine();
//                    System.out.println("enter movi genre");
//                    genre = sc.next();
//                    System.out.println("enter a movi rating");
//                    rating = sc.nextFloat();
//                    System.out.println("enter movi duration (in min): ");
//                    duration = sc.nextInt();
//                    m.insertMovi(title, genre, duration, rating);
//                    break;
//                case 2:
//                    m.showMovies();
//                    break;
//
//                case 3:
//                    // Set a movie available for showtime
//                    System.out.println("Enter Movie ID:");
//                    movieid = sc.nextInt();
//                    System.out.println("Enter Theater ID:");
//                    theater_id = sc.nextInt();
//                    System.out.println("Enter the Screen Id:");
//                    screen_id = sc.nextInt();
//                    System.out.println("Enter Showtime hour:");
//                    showtime_hour = sc.nextInt();
//                    System.out.println("Enter Showtime minute:");
//                    showtime_min = sc.nextInt();
//                    LocalDateTime localDateTime = LocalDateTime.now().withHour(showtime_hour).withMinute(showtime_min).withSecond(0);
//                    showtime = Timestamp.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//                    System.out.println("Enter the total number of seats available:");
//                    total_seats = sc.nextInt();
//                    System.out.println("Enter the available number of seats:");
//                    available_seats = sc.nextInt();
//
//                    // Insert the showtime and get the generated showtime_id
//                    int showtime_id = st.insertShowtime(movieid, theater_id, screen_id, showtime, available_seats);
//
//                    if (showtime_id != -1) {
//                        System.out.println("Showtime added successfully with Showtime ID: " + showtime_id);
//                        // Store showtime_id to use later in case 9
//                    } else {
//                        System.out.println("Failed to insert showtime. Cannot add seats.");
//                    }
//                    break;
//
//
//                case 4:
//                    //see all shows
//                    st.showShowtimes();
//                    break;
//
//                case 5:
//                    //add theaters
//
//                    System.out.println("enter the name of the theater");
//                    sc.nextLine();
//                    theater_name = sc.nextLine();
//                    System.out.println("enter the theater location");
//                    adress = sc.nextLine();
//                    t.insertTheater(theater_name, adress);
//                    break;
//                case 6:
//                    //see all theaters
//                    t.showTheateres();
//                    break;
//                case 7:
//                    //ADD SCREEN TO THE THEATER
    ////                    System.out.println("Enter Theater ID:");
    ////                    theater_id = sc.nextInt();
    ////                    System.out.println("Enter Screen Name:");
    ////                    String screenName = sc.next();
    ////                    System.out.println("Enter total seats available:");
    ////                    int capacity = sc.nextInt();
    ////                    scr.insertScreen(theater_id,screenNumber, totalSeats);
    ////                    scr.addShowToScreen(screenName);
    ////                    break;
//                    System.out.println("Enter Theater ID:");
//                    theater_id = sc.nextInt();
//                    System.out.println("Enter Screen Number:");
//                    screenNumber = sc.nextInt();
//                    System.out.println("Enter Total Seats available:");
//                    totalSeats = sc.nextInt();
//
//                    // Insert screen with validation
//                    scr.insertScreen(theater_id, screenNumber, totalSeats);
//
//                    // Check and add shows to the screen to meet the requirement of 4 shows
//                    scr.addShowToScreen(screenNumber);
//                    break;
//                case 8:
//                    //see screens in the theater
//                    System.out.println("Enter Theater ID:");
//                    theater_id = sc.nextInt();
//                    scr.showScreensInTheater(theater_id);
//                    break;
//
//                case 9:
//                    // Add seats to the screen for a showtime
//                    System.out.println("Enter Screen ID:");
//                    screen_id = sc.nextInt();
//                    System.out.println("Enter Level of Seats (e.g., A, B, C...):");
//                    level = sc.next();
//                    System.out.println("Enter Total Number of Seats:");
//                    totalSeats = sc.nextInt();
//
//                    // Assuming you have inserted a showtime earlier, we need to get the showtime_id for that showtime
//                    System.out.println("Enter Showtime ID (the one assigned when showtime was inserted):");
//                    int showtimeid = sc.nextInt();  // Fetch the showtime_id manually or keep track of it after inserting the showtime
//
//                    // Now insert seats for that showtime
//                    se.insertSeats(screen_id, level, totalSeats, showtimeid);
//                    break;
//
//                case 10:
//                    System.out.println("Enter Screen ID:");
//                    screen_id = sc.nextInt();
//                    se.showSeatsInScreen(screen_id);
//                    break;
//                case 11:
//                    // Show movies on screens
//                    scr.showMoviesOnScreens();
//                    break;
//                case 12:
//                    // Remove movie from screen
//                    System.out.println("Enter Movie ID to remove:");
//                    int movie_id = sc.nextInt();
//                    System.out.println("Enter Screen ID to remove movie from:");
//                    int screen_id = sc.nextInt();
//                    scr.removeMovieFromScreen(movie_id, screen_id);
//                    break;
//                case 13:
//                    // Remove theater
//                    System.out.println("Enter Theater ID to remove:");
//                    int theater_id = sc.nextInt();
//                    t.removeTheater(theater_id);
//                    break;
//
//                case 14:
//                    //terminate the program
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("wrong choice entered ");
//                    break;
//
//            }
//        }
//    }
//}
    //        Scanner sc = new Scanner(System.in);
//        DatabaseOperation db = new DatabaseOperation();
//
//        // Show the login or signup options
//        System.out.println("----- Welcome to the Movie Booking System -----");
//        System.out.println("Press 1 to Login");
//        System.out.println("Press 2 to Signup");
//        int choice = sc.nextInt();
//
//        if (choice == 1) {
//            // Login
//            System.out.println("Enter your username:");
//            sc.nextLine();  // Consume newline
//            String username = sc.nextLine();
//
//            System.out.println("Enter your password:");
//            String password = sc.nextLine();
//
//            // Authenticate the user
//            if (db.authenticateUser(username, password)) {
//                int userID = db.getUserID(username);
//                System.out.println("Login successful! Your userID is: " + userID);
//                // Check if the user is an admin
//                if (db.isAdmin(userID)) {
//                    Admin admin = new Admin();
//                    admin.adminMenu(userID); // Show admin menu
//                } else {
//                    User user = new User();
//                    user.userMenu(userID); // Show user menu
//                }
//            } else {
//                System.out.println("Invalid username or password. Please try again.");
//            }
//        } else if (choice == 2) {
//            // Signup
//            System.out.println("Enter your username:");
//            sc.nextLine();  // Consume newline
//            String username = sc.nextLine();
//
//            System.out.println("Enter your password:");
//            String password = sc.nextLine();
//
//            System.out.println("Enter your phone number:");
//            String phone = sc.nextLine();
//
//            // Add new user to the database
//            boolean isRegistered = db.registerUser(username, password, phone);
//
//            if (isRegistered) {
//                System.out.println("Signup successful! You can now login. ");
//
//
//                int userID = db.getUserID(username);
//                System.out.println("Signup successful! Your userID is: " + userID);
//
//                User user = new User();
//                user.userMenu(userID);
//
//            } else {
//                System.out.println("Signup failed! Please try again.");
//            }
//        } else {
//            System.out.println("Invalid choice. Exiting...");
//        }
//
//------showtime-----


    public void showShowtimes() {
//        String sql = "select* from showtime";
//        ResultSet rs = db.getRecords(sql);
//        try {
//            while (rs.next()) {
//                System.out.println("showtime id:" + rs.getInt("SHOWID"));
//                System.out.println("movi id:" + rs.getInt("MOVIID"));
//                System.out.println("Scrren id:"+ rs.getInt("screenId"));
//                System.out.println("theater Id :" + rs.getString("THEATERID"));
//                System.out.println("showtime: " + rs.getTimestamp("showtime"));
//
//                System.out.println("availableSeats:" + rs.getInt("AVAILABLE_SEATS"));
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
//      }
//    }
//public void showShowtimes() {
//    String sql = "SELECT * FROM showtime";
//    ResultSet rs = db.getRecords(sql); // Get the ResultSet from DatabaseOperation
//    try {
//        if (rs != null) {
//            System.out.println("----- Available Showtimes -----");
//            while (rs.next()) {
//                System.out.println("Showtime ID: " + rs.getInt("SHOWID"));
//                System.out.println("Movie ID: " + rs.getInt("MOVIID"));
//                System.out.println("Screen ID: " + rs.getInt("SCREENID"));
//                System.out.println("Theater ID: " + rs.getInt("THEATERID"));
//                System.out.println("Showtime: " + rs.getTimestamp("showtime"));
//                System.out.println("Available Seats: " + rs.getInt("AVAILABLE_SEATS"));
//                System.out.println("--------------------------------");
//            }
//        }
//    } catch (SQLException e) {
//        System.out.println("Error fetching showtimes: " + e.getMessage());
//    } finally {
//        try {
//            if (rs != null) rs.close();  // Close the ResultSet after use
//        } catch (SQLException e) {
//            System.out.println("Error closing ResultSet: " + e.getMessage());
//        }
//    }
//}
        //    public void availableSeatsInShowtime(int showtimeID) {
//        String sql = "SELECT * FROM seats WHERE showtime_id = ? AND isBooked = false";
//        ResultSet rs = db.getRecords(sql, showtimeID);
//        try {
//            while (rs.next()) {
//                String level = rs.getString("level");
//                int seatNumber = rs.getInt("seat_number");
//                System.out.println("Level: " + level + ", Seat Number: " + seatNumber);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
        //    public void availableSeatsInShowtime(int showtimeID) {
//    // Query to fetch available seats for a given showtime
//    String sql = "SELECT * FROM seats WHERE showtime_id = ? AND isBooked = false";
//    ResultSet rs = db.getRecords(sql, showtimeID);
//    System.out.println("Available Seats for Showtime ID: " + showtimeID);
//    try {
//        boolean foundSeats = false;
//        while (rs.next()) {
//            int seatID = rs.getInt("seat_id");
//            String level = rs.getString("level");
//            int seatNumber = rs.getInt("seat_number");
//            System.out.println("Seat ID: " + seatID + ", Level: " + level + ", Seat Number: " + seatNumber);
//            foundSeats = true;
//        }
//        if (!foundSeats) {
//            System.out.println("No available seats for this showtime.");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    } finally {
//        try {
//            if (rs != null) {
//                rs.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//    public void insertSeats(int screenId, String level, int numSeats) {
//        for (int seatNumber = 1; seatNumber <= numSeats; seatNumber++) {
//
//            String sql = "INSERT INTO seats (screen_id, level, seat_number, isBooked, seat_status) VALUES (?, ?, ?, ?, ?)";
//            Object[] values = {screenId, level, seatNumber, false, "available"};
//            db.executeUpdate(sql, values);
//        }
//        System.out.println("Seats added successfully for level " + level + "!");
//    }
//    public void bookTicket(int userID) {
//
//
//        System.out.println("Enter userID for booking:");
//        int useID = sc.nextInt();
//        System.out.println("Available Showtimes: ");
//        st.showShowtimes();
//
//        // Display available seats for the selected showtime
//        System.out.println();
//        Seat se = new Seat();
//        System.out.println("enter a show time id ");
//        int showtimeID=sc.nextInt();
//        se. availableSeatsInShowtime(showtimeID);
//        System.out.println("list of movies available in the thaeter");
//        Movi m=new Movi();
//        m.showMovies();
////        System.out.println("enter a screen id");
////        int screenID = sc.nextInt();
////
////         se.showSeatsInScreen(screenID);
//        System.out.println("Enter Seat ID to book:");
//
//        int seatID = sc.nextInt();
//
//        String sqlCheck = "SELECT * FROM seats WHERE seat_id = ? AND isBooked = false";
//        boolean isAvailable = false;
//        ResultSet rs = null; // Declare ResultSet outside the try block
//        try (Connection conn = db.connectToDatabase();
//             PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
//            ps.setInt(1, seatID);
//            rs = ps.executeQuery(); // Initialize rs here
//            if (rs.next()) {
//                isAvailable = true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Close the ResultSet
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (isAvailable) {
//            // Proceed with booking
//            String sql = "INSERT INTO booking (USERID,  seatID,SHOWID) VALUES (?, ?, ?)";
//            Object[] values = {userID, seatID,showtimeID};
//            int rowsAffected = db.executeUpdate(sql, values);
//
//            if (rowsAffected > 0) {
//                System.out.println("Ticket booked successfully!");
//
//                // Update the seat status to booked
//                System.out.println("here");
//                updateSeatBooking(seatID, true);
//            } else {
//                System.out.println("Booking failed. Please try again.");
//            }
//        } else {
//            System.out.println("Seat is already booked. Please choose another seat.");
//        }
//    }
    }
}

//    public void checkAndCreateDefaultAdmin() {
//        String checkAdminSQL = "SELECT COUNT(*) FROM user WHERE isAdmin = true";
//
//        try (Connection conn = databaseOperation.connectToDatabase();
//             PreparedStatement ps = conn.prepareStatement(checkAdminSQL)) {
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next() && rs.getInt(1) == 0) {
//
//                String insertAdminSQL = "INSERT INTO user (username, password, isAdmin, phone) VALUES (?, ?, ?, ?)";
//                try (PreparedStatement insertPs = conn.prepareStatement(insertAdminSQL)) {
//                    insertPs.setString(1, "admin");
//                    insertPs.setString(2, "admin123");
//                    insertPs.setBoolean(3, true);
//                    insertPs.setString(4, "1234567890");
//                    int rowsAffected = insertPs.executeUpdate();
//                    if (rowsAffected > 0) {
//                        System.out.println("Default admin created successfully.");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }