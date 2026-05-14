import java.util.ArrayList;
import java.util.List;


public abstract class PanelOperation {
    protected static List<Room> rooms = new ArrayList<>(); // List to store rooms

    // Method to add a room (implementation to be defined in subclasses)
    public void addRoom() {
    }

    // Method to remove a room, taking a LoginSystem object as a parameter (implementation to be defined in subclasses)
    public void removeRoom(LoginSystem loginSystem) {
    }

    // Method to view all rooms (implementation to be defined in subclasses)
    public void viewAllRooms() {
    }

    // Method to view available rooms (implementation to be defined in subclasses)
    public void viewAvailableRooms() {
    }

    // Method for a user to book a room (implementation to be defined in subclasses)
    public void bookARoom(User user) {
    }

    // Method to check reservations using the LoginSystem object (implementation to be defined in subclasses)
    public void checkReservations(LoginSystem L) {
    }

    // Method to view a specific user's reservations (implementation to be defined in subclasses)
    public void ViewYourReservations(User user) {
    }

    // Method for an admin to cancel a reservation (implementation to be defined in subclasses)
    public void cancelReservationAdmin(LoginSystem L) {
    }

    // Method for a guest to cancel their reservation (implementation to be defined in subclasses)
    public void cancelReservationGuest(User user) {
    }

    // Method to get the list of rooms
    public List<Room> getRooms() {
        return rooms; // Return the list of rooms
    }
}