package src;


import java.util.Scanner;

class MovieTicketDriver {
    Scanner sc = new Scanner(System.in);
    DatabaseOperation databaseOperation = new DatabaseOperation();


    public void user_signup() {
        System.out.println("Enter your username: ");
        String username = sc.next();

        System.out.println("Enter your password: ");
        String password = sc.next();

        System.out.println("Enter your phone number: ");
        String phone = sc.next();


        int isAdmin = 0;

        String sql = "INSERT INTO user(username, password, isAdmin, phone) VALUES(?, ?, ?, ?)";
        Object[] values = {username, password, isAdmin, phone};
        int rowsAffected = databaseOperation.executeUpdate(sql, values);

        if (rowsAffected > 0) {
            System.out.println("User registered successfully");


            int userID = databaseOperation.getUserID(username);

            if (userID != -1) {
                System.out.println("Welcome, " + username + "! Your User ID is: " + userID);


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
        String pass_real = databaseOperation.validatePass(sql, username);

        if (password.equals(pass_real)) {
            System.out.println("Login Successful!");
            User u = new User();

            int userID = databaseOperation.getUserID(username);
            u.userMenu(userID);
        } else {
            System.out.println("Invalid password! Try again!");
        }
    }


    public  void adminLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();


        if (databaseOperation.validateAdminLogin(username, password)) {
            System.out.println("Login Successful!");
            int userID = databaseOperation.getUserID(username);
            Admin admin = new Admin();
            admin.adminMenu(userID);

        } else {
            System.out.println("Invalid credentials or you are not an admin.");
        }
    }

}


