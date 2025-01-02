package src;

import java.util.Scanner;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;

public class Admin extends User {
    Scanner sc = new Scanner(System.in);
    int choice;
    int UserID;
    String title;
    String genre;
    float rating;
    int duration;
    int theater_id;
    int showtime_hour;
    int showtime_min;
    Timestamp showtime;
    String level;
    int movieid;
    int screen_id;
    int screenNumber;
    int total_seats;
    int available_seats;
    String adress;
    String theater_name;
    int totalSeats;
    Movi m = new Movi();
    Theater t = new Theater();
    Showtime st = new Showtime();
    DatabaseOperation db = new DatabaseOperation();
    Screen scr = new Screen();
    Seat se = new Seat();

    public int takeUserId() {
        System.out.println("enter user id");
        return sc.nextInt();
    }


        public void adminMenu(int userID) {
            // Check if the user is an admin
            if (!db.isAdmin(userID)) {
                System.out.println("You are not authorized to access the admin menu.");
                return;
            }





        while (true) {

            System.out.println("-----ADMIN MENU-------");
            System.out.println("PRESS 1 TO ADD MOVIES");
            System.out.println("PRESS 2 TO SEE ALL MOVIES");
            System.out.println("PRESS 3 TO PUT A MOVI FOR A SHOW TIME");
            System.out.println("PRESS 4 TO SEE ALL SHOWTIME");
            System.out.println("PRESS 5 TO ADD THEATERS");
            System.out.println("PRESS 6 TO SEE ALL THEATERS");
            System.out.println("PRESS 7 TO ADD SCREEN TO THEATERS");
            System.out.println("PRESS 8 TO SEE SCREENS IN THE THEATERS");
            System.out.println("PRESS 9 TO ADD SEATS TO SCREENS");
            System.out.println("PRESS 10 TO SEE SEATS IN THE SCREEN ");
            System.out.println("PRESS 11 TO VIEW MOVIES ON SCREENS");
            System.out.println("PRESS 12 TO REMOVE MOVIES FROM SCREEN");
            System.out.println("PRESS 13 TO REMOVE THEATER");
            System.out.println("PRESS 14 TO EXIT");
            System.out.println("ENTER YOUR CHOICE");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // add movies
                    System.out.println("enter a movi title");
                    sc.nextLine();
                    title = sc.nextLine();
                    System.out.println("enter movi genre");
                    genre = sc.next();
                    System.out.println("enter a movi rating");
                    rating = sc.nextFloat();
                    System.out.println("enter movi duration (in min): ");
                    duration = sc.nextInt();
                    m.insertMovi(title, genre, duration, rating);
                    break;
                case 2:
                    m.showMovies();
                    break;

                case 3:
                    // Set a movie available for showtime
                    System.out.println("Enter Movie ID:");
                    movieid = sc.nextInt();
                    System.out.println("Enter Theater ID:");
                    theater_id = sc.nextInt();
                    System.out.println("Enter the Screen Id:");
                    screen_id = sc.nextInt();
                    System.out.println("Enter Showtime hour:");
                    showtime_hour = sc.nextInt();
                    System.out.println("Enter Showtime minute:");
                    showtime_min = sc.nextInt();
                    LocalDateTime localDateTime = LocalDateTime.now().withHour(showtime_hour).withMinute(showtime_min).withSecond(0);
                    showtime = Timestamp.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    System.out.println("Enter the total number of seats available:");
                    total_seats = sc.nextInt();
                    System.out.println("Enter the available number of seats:");
                    available_seats = sc.nextInt();

                    // Insert the showtime and get the generated showtime_id
                    int showtime_id = st.insertShowtime(movieid, theater_id, screen_id, showtime, available_seats);

                    if (showtime_id != -1) {
                        System.out.println("Showtime added successfully with Showtime ID: " + showtime_id);
                        // Store showtime_id to use later in case 9
                    } else {
                        System.out.println("Failed to insert showtime. Cannot add seats.");
                    }
                    break;


                case 4:
                    //see all shows
                    st.showShowtimes();
                    break;

                case 5:
                    //add theaters

                    System.out.println("enter the name of the theater");
                    sc.nextLine();
                    theater_name = sc.nextLine();
                    System.out.println("enter the theater location");
                    adress = sc.nextLine();
                    t.insertTheater(theater_name, adress);
                    break;
                case 6:
                    //see all theaters
                    t.showTheateres();
                    break;
                case 7:
                    //ADD SCREEN TO THE THEATER
//                    System.out.println("Enter Theater ID:");
//                    theater_id = sc.nextInt();
//                    System.out.println("Enter Screen Name:");
//                    String screenName = sc.next();
//                    System.out.println("Enter total seats available:");
//                    int capacity = sc.nextInt();
//                    scr.insertScreen(theater_id,screenNumber, totalSeats);
//                    scr.addShowToScreen(screenName);
//                    break;
                    System.out.println("Enter Theater ID:");
                    theater_id = sc.nextInt();
                    System.out.println("Enter Screen Number:");
                    screenNumber = sc.nextInt();
                    System.out.println("Enter Total Seats available:");
                    totalSeats = sc.nextInt();

                    // Insert screen with validation
                    scr.insertScreen(theater_id, screenNumber, totalSeats);

                    // Check and add shows to the screen to meet the requirement of 4 shows
                    scr.addShowToScreen(screenNumber);
                    break;
                case 8:
                    //see screens in the theater
                    System.out.println("Enter Theater ID:");
                    theater_id = sc.nextInt();
                    scr.showScreensInTheater(theater_id);
                    break;

                case 9:
                    // Add seats to the screen for a showtime
                    System.out.println("Enter Screen ID:");
                    screen_id = sc.nextInt();
                    System.out.println("Enter Level of Seats (e.g., A, B, C...):");
                    level = sc.next();
                    System.out.println("Enter Total Number of Seats:");
                    totalSeats = sc.nextInt();

                    // Assuming you have inserted a showtime earlier, we need to get the showtime_id for that showtime
                    System.out.println("Enter Showtime ID (the one assigned when showtime was inserted):");
                    int showtimeid = sc.nextInt();  // Fetch the showtime_id manually or keep track of it after inserting the showtime

                    // Now insert seats for that showtime
                    se.insertSeats(screen_id, level, totalSeats, showtimeid);
                    break;

                case 10:
                    System.out.println("Enter Screen ID:");
                    screen_id = sc.nextInt();
                    se.showSeatsInScreen(screen_id);
                    break;
                case 11:
                    // Show movies on screens
                    scr.showMoviesOnScreens();
                    break;
                case 12:
                    // Remove movie from screen
                    System.out.println("Enter Movie ID to remove:");
                    int movie_id = sc.nextInt();
                    System.out.println("Enter Screen ID to remove movie from:");
                    int screen_id = sc.nextInt();
                    scr.removeMovieFromScreen(movie_id, screen_id);
                    break;
                case 13:
                    // Remove theater
                    System.out.println("Enter Theater ID to remove:");
                    int theater_id = sc.nextInt();
                    t.removeTheater(theater_id);
                    break;

                case 14:
                    //terminate the program
                    System.exit(0);
                    break;
                default:
                    System.out.println("wrong choice entered ");
                    break;

            }
        }
    }
}
