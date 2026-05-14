import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginSystem {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_LIGHT_CYAN = "\u001B[96m";

    private static Map<String, User> users; // Map to store user information

    // Constructor to initialize the LoginSystem and add some sample users
    public LoginSystem() {
        users = new HashMap<>();
        // Adding some sample users for testing
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
    }

    // Method to handle user login
    public User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(ANSI_LIGHT_CYAN+"Enter username: "); // Prompt for username
        String username = scanner.nextLine(); // Read username input
        System.out.print("Enter password: "); // Prompt for password
        String password = scanner.nextLine(); // Read password input

        User user = users.get(username); // Retrieve user by username

        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful! Welcome, " + username + "!"+ANSI_RESET); // Successful login message
            return user; // Return the logged-in user
            // Place to call subsequent functions, e.g., enter main menu
        } else {
            System.out.println("Invalid username or password. Please try again."+ANSI_RESET); // Error message for failed login
            return null; // Return null for failed login attempt
        }
    }

    // Method to handle user registration
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(ANSI_LIGHT_CYAN+"Enter a username: "); // Prompt for username
        String username = scanner.nextLine(); // Read username input

        // Check if the username already exists
        if (users.containsKey(username)) {
            System.out.println("Username already taken. Please choose another one."+ANSI_RESET); // Error message if username exists
            return; // Exit registration method if username is taken
        }

        System.out.print("Enter a password: "); // Prompt for password
        String password = scanner.nextLine(); // Read password input

        // Create a new user and store it in the Map
        users.put(username, new User(username, password)); // Add new user to the Map
        System.out.println("Registration successful! You can now log in with your credentials."+ANSI_RESET); // Success message for registration
    }

    // Method to get all registered users
    public Collection<User> getUsers() {
        return users.values(); // Return a collection of all user objects
    }
}