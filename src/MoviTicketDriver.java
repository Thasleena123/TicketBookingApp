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


    // Admin login method
    public static void adminLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        // Directly validate admin credentials
        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Login Successful!");

            // Bypass the database check and grant access to the admin menu
            Admin admin = new Admin();
            admin.adminMenu(1); // Assuming 1 is a valid user ID
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
}



