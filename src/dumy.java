package src;

public class dumy {
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