package src;


import java.util.Scanner;

class MovieTicketDriver {
    Scanner sc = new Scanner(System.in);
    DatabaseOperation db = new DatabaseOperation();

    // Methods to sign up and login for user class
    public void user_signup() {
        System.out.println("Enter your username: ");
        String username = sc.next();

        System.out.println("Enter your password: ");
        String password = sc.next();

        System.out.println("Enter your phone number: ");
        String phone = sc.next();

        // `isadmin` is default 0 for users
        int isAdmin = 0;

        String sql = "INSERT INTO user(username, password, isAdmin, phone) VALUES(?, ?, ?, ?)";
        Object[] values = {username, password, isAdmin, phone};
        int rowsAffected = db.executeUpdate(sql, values);

        if (rowsAffected > 0) {
            System.out.println("User registered successfully");

            // Fetch userid for the newly registered user
            int userID = db.getUserID(username);

            if (userID != -1) {
                System.out.println("Welcome, " + username + "! Your User ID is: " + userID);

                // Immediately call the user menu
                User user = new User();
                user.userMenu(userID);
            } else {
                System.out.println("Error fetching user ID. Please try logging in.");
            }
        } else {
            System.out.println("Something went wrong. Sign-up failed.");
        }
    }


    public void user_login() {
        System.out.println("Enter your username: ");
        String username = sc.next();

        System.out.println("Enter your password: ");
        String password = sc.next();

        String sql = "SELECT password FROM user WHERE username = ?";
        String pass_real = db.validatePass(sql, username);

        if (password.equals(pass_real)) {
            System.out.println("Login Successful!");
            User u = new User();
            // sql = "SELECT userid FROM user WHERE username = ?";
            int userID = db.getUserID(username);
            u.userMenu(userID);
        } else {
            System.out.println("Invalid password! Try again!");
        }
    }



    public void admin_login() {
        System.out.println("Enter your username: ");
        String username = sc.next();

        System.out.println("Enter your password: ");
        String password = sc.next();

        String sql = "SELECT password FROM user WHERE username = ? AND isadmin = 1";
        String pass_real = db.validatePass(sql, username);

        if (password.equals(pass_real)) {
            System.out.println("Login Successful!");
            Admin a = new Admin();
            int userid = db.getUserID(username);
//            a.adminMenu(userid);

        } else {
            System.out.println("Invalid password or not an admin! Try again!");
        }
    }
}


