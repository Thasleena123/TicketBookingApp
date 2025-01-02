package src;

import java.sql.Timestamp;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {


        //-------------sreen entry----------------
//        Screen sc=new Screen();
//
//        sc.insertScreen(1,1, 150);
//
//
//        sc.insertScreen(1, 2, 180);
//
//
//        sc.insertScreen(2, 2, 200);
//
//
//        sc.insertScreen(2, 2, 220);
//
//

        //------------------------theater entry-------------------------------------------------
//        Theater t=new Theater();
//
//        t.insertTheater("PVR Cinemas", "kochi");
//
//
//       t. insertTheater("INOX", "Delhi");
//
//
//        t.insertTheater("Cinepolis", "Bangalore");
//
//
//        t.insertTheater("Movie Time", "Chennai");
//----------------movi entry------------------
//        Movi m= new Movi();
//        m.insertMovi("lion","comedy",120,4.8f);

        //--------------------------insert showtimes-----------------------------------------------
//        Showtime showtime = new Showtime();
//
//
//        showtime.insertShowtime(1, 1, 1, Timestamp.valueOf("2024-12-31 14:00:00"), 95);
//
//
//        showtime.insertShowtime(9, 1, 2, Timestamp.valueOf("2025-01-01 18:30:00"), 85);
//
//
//        showtime.insertShowtime(12, 4, 1, Timestamp.valueOf("2025-01-02 16:00:00"), 100);
//
//
//        showtime.insertShowtime(17, 6, 2, Timestamp.valueOf("2025-01-03 20:00:00"), 75);
//
//
//-------------------booking------------------------------------
            Scanner sc2 = new Scanner(System.in);
           DatabaseOperation db=new DatabaseOperation();
           MovieTicketDriver mtd=new MovieTicketDriver();
           User u=new User();
            int choice;
            System.out.println("----- Movie Ticket Booking System -----");
            System.out.println("Press 1 to sign up as user.");
            System.out.println("Press 2 to login as user.");
            System.out.println("Press 3 to login as admin.");
            System.out.println("---------------------------------------");
            System.out.print("Enter your choice: ");
            choice = sc2.nextInt();
            switch (choice) {
                case 1:
                    mtd.user_signup();

                    break;
                case 2:
                    mtd.user_login();
                    break;

                case 3:
                    // Admin login
                  mtd.admin_login();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }


        // admin details------------------------------


//        User user = new User();
//        user.insertAdmin();

    }
}