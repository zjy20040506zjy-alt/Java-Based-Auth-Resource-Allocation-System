import java.util.ArrayList;
import java.util.List;

public class User {
    private String username; // Username of the user
    private String password; // Password for the user account
    private List<Room> reservations; // List of room reservations

    // Constructor to initialize a new user with a username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.reservations = new ArrayList<>(); // Initialize the reservations list
    }

    // Method to add a room reservation to the user's list
    public void addReservation(Room room) {
        reservations.add(room); // Add the room to the reservations list
        System.out.println("Room number " + room.getRoomNumber() + " has been added to your reservations.");
    }

    // Get the list of reserved rooms
    public List<Room> getReservations() {
        return reservations; // Return the reservations list
    }

    // Get the username of the user
    public String getUsername() {
        return username; // Return the username
    }

    // Get the password of the user
    public String getPassword() {
        return password; // Return the password
    }
}