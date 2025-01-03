package src;

import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    String address;
    String theater_name;
    int totalSeats;
    Movie m = new Movie();
    Theater t = new Theater();
    Showtime st = new Showtime();
    DatabaseOperation db = new DatabaseOperation();
    Screen screen = new Screen();
    Seat se = new Seat();


    public int takeUserId() {
        System.out.println("Enter user ID: ");
        return sc.nextInt();
    }

    // Admin menu
    public void adminMenu(int userID) {
        // Check if the user is an admin
//        if (!db.isAdmin(userID)) {
//            System.out.println("You are not authorized to access the admin menu.");
//            return;
        // }

        while (true) {
            System.out.println("-----ADMIN MENU-------");
            System.out.println("Press 1 to Add Movies");
            System.out.println("Press 2 to See All Movies");
            System.out.println("Press 3 to Set Movie Showtime");
            System.out.println("Press 4 to See All Showtimes");
            System.out.println("Press 5 to Add Theaters");
            System.out.println("Press 6 to See All Theaters");
            System.out.println("Press 7 to Add Screen to Theater");
            System.out.println("Press 8 to See Screens in Theater");
            System.out.println("Press 9 to Add Seats to Screen");
            System.out.println("Press 10 to See Seats in Screen");
            System.out.println("Press 11 to View Movies on Screens");
            System.out.println("Press 12 to Remove Movie from Screen");
            System.out.println("Press 13 to Remove Theater");
            System.out.println("press 14 to add new admin");
            System.out.println("Press 15 to Exit");
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // Add movie
                    System.out.println("Enter movie title: ");
                    sc.nextLine(); // Consume leftover newline character
                    title = sc.nextLine();
                    System.out.println("Enter movie genre: ");
                    genre = sc.next();
                    System.out.println("Enter movie rating: ");
                    rating = sc.nextFloat();
                    System.out.println("Enter movie duration (in minutes): ");
                    duration = sc.nextInt();
                    m.insertMovie(title, genre, duration, rating);
                    break;

                case 2:
                    // Show all movies
                    m.showMovies();
                    break;

                case 3:
                    // Set movie for showtime
                    System.out.println("Enter Movie ID: ");
                    movieid = sc.nextInt();
                    System.out.println("Enter Theater ID: ");
                    theater_id = sc.nextInt();
//
                    System.out.println("Enter Screen ID: ");
                    screen_id = sc.nextInt();
                    System.out.println("Enter Showtime Date (yyyy-MM-dd): ");
                    String showtimeDate = sc.next(); // Example: "2025-01-03"

                    System.out.println("Enter Showtime Hour: ");
                    int showtime_hour = sc.nextInt();

                    System.out.println("Enter Showtime Minute: ");
                    int showtime_min = sc.nextInt();

                    // User input for start date and end date
                    System.out.println("Enter Start Date (yyyy-MM-dd): ");
                    String startDateStr = sc.next(); // Example: "2025-01-03"

                    System.out.println("Enter End Date (yyyy-MM-dd): ");
                    String endDateStr = sc.next(); // Example: "2025-01-03"

                    // Parse the user-provided date and time
                    LocalDate date = LocalDate.parse(showtimeDate);
                    LocalDateTime localShowtime = date.atTime(showtime_hour, showtime_min);

                    // Parse the start and end dates
                    LocalDate startDate = LocalDate.parse(startDateStr);
                    LocalDateTime startDateTime = startDate.atTime(showtime_hour, showtime_min);

                    LocalDate endDate = LocalDate.parse(endDateStr);
                    LocalDateTime endDateTime = endDate.atTime(showtime_hour, showtime_min);

                    // Convert to Timestamp
                    Timestamp showtime = Timestamp.from(localShowtime.atZone(ZoneId.systemDefault()).toInstant());
                    Timestamp startTimestamp = Timestamp.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    Timestamp endTimestamp = Timestamp.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

                    // Output the results (you would insert them into the database)
                    System.out.println("Showtime: " + showtime);
                    System.out.println("Start Date: " + startTimestamp);
                    System.out.println("End Date: " + endTimestamp);


                    break;

                case 4:
                    // Show all showtimes
                    st.showShowtimes();
                    break;

                case 5:
                    // Add theater
                    System.out.println("Enter theater name: ");
                    sc.nextLine(); // Consume leftover newline character
                    theater_name = sc.nextLine();
                    System.out.println("Enter theater address: ");
                    address = sc.nextLine();
                    t.insertTheater(theater_name, address);
                    break;

                case 6:
                    // Show all theaters
                    t.showTheateres();
                    break;

                case 7:
                    // Add screen to theater

                    System.out.println("Enter Theater ID: ");
                    theater_id = sc.nextInt();


                    if (!databaseOperation.isTheaterIdValid(theater_id)) {
                        System.out.println("Invalid Theater ID. Cannot add a screen.");
                        break;
                    }
                System.out.println("Enter Screen Number: ");
                screenNumber = sc.nextInt();
                System.out.println("Enter Total Seats: ");
                totalSeats = sc.nextInt();

                screen.insertScreen(theater_id, screenNumber, totalSeats);
                break;

            case 8:
                // Show screens in theater
                System.out.println("Enter Theater ID: ");
                theater_id = sc.nextInt();
                screen.showScreensInTheater(theater_id);
                break;

            case 9:
                // Add seats to screen
                System.out.println("Enter Screen ID: ");
                screen_id = sc.nextInt();
                System.out.println("Enter Seat Level (A, B, C, etc.): ");
                level = sc.next();
                System.out.println("Enter Total Seats: ");
                totalSeats = sc.nextInt();
                System.out.println("Enter Showtime ID: ");
                int showtimeid = sc.nextInt();
                se.insertSeats(screen_id, level, totalSeats, showtimeid);
                break;

            case 10:
                // Show seats in screen
                System.out.println("Enter Screen ID: ");
                screen_id = sc.nextInt();
                se.showSeatsInScreen(screen_id);
                break;

            case 11:
                // Show movies on screens
                screen.showMoviesOnScreens();
                break;

            case 12:
                // Remove movie from screen
                System.out.println("Enter Movie ID to remove: ");
                int movie_id = sc.nextInt();
                System.out.println("Enter Screen ID to remove from: ");
                int screen_id_remove = sc.nextInt();
                screen.removeMovieFromScreen(movie_id, screen_id_remove);
                break;

            case 13:
                // Remove theater
                System.out.println("Enter Theater ID to remove: ");
                int theater_id_remove = sc.nextInt();
                t.removeTheater(theater_id_remove);
                break;
            case 14:
                addNewAdmin(userID);
                break;

            case 15:
                // Exit the program
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }
}
}
