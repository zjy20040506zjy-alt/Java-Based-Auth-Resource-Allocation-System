import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminPanel extends PanelOperation {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_LIGHT_CYAN = "\u001B[96m";

    // Method to add a room
    public void addRoom() {
        Scanner input = new Scanner(System.in);

        // Input room number
        int roomNumber;
        while (true) {
            System.out.println("Please enter room number:");
            String roomNumberInput = input.nextLine();

            try {
                roomNumber = Integer.parseInt(roomNumberInput); // Convert input to integer
                if (roomNumber <= 0) {
                    System.out.println(ANSI_LIGHT_CYAN + "Room number must be a positive number. Please try again." + ANSI_RESET);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(ANSI_LIGHT_CYAN + "Invalid room number. Please enter a valid number." + ANSI_RESET);
                continue;
            }

            // Check if the room number already exists using a temporary variable
            final int tempRoomNumber = roomNumber; // Create a valid final temporary variable
            boolean roomExists = rooms.stream()
                    .anyMatch(room -> room.getRoomNumber() == tempRoomNumber);
            if (roomExists) {
                System.out.println(ANSI_LIGHT_CYAN+"Room number " + roomNumber + " already exists. Please enter a different room number."+ANSI_RESET);
            } else {
                break; // Room number does not exist, exit the loop
            }
        }

        // Input room category
        Room.Category roomCategory;
        String roomCategoryOption;
        do {
            System.out.println(ANSI_LIGHT_CYAN+"Please enter room category (1. Deluxe 2. Suite 3. Standard):"+ANSI_RESET);
            roomCategoryOption = input.nextLine();
            switch (roomCategoryOption) {
                case "1":
                    roomCategory = Room.Category.DELUXE;
                    break;
                case "2":
                    roomCategory = Room.Category.SUITE;
                    break;
                case "3":
                    roomCategory = Room.Category.STANDARD;
                    break;
                default:
                    System.out.println(ANSI_LIGHT_CYAN+"Invalid choice, please try again."+ANSI_RESET);
                    roomCategory = null; // Set roomCategory to null
            }
        } while (roomCategory == null); // Continue looping until a valid choice is made

        // Input room type
        Room.RoomType roomType;
        String roomTypeOption;
        do {
            System.out.println(ANSI_LIGHT_CYAN+"Please enter room type (1. SINGLE 2. DOUBLE 3. SUITE):"+ANSI_RESET);
            roomTypeOption = input.nextLine();
            switch (roomTypeOption) {
                case "1":
                    roomType = Room.RoomType.SINGLE;
                    break;
                case "2":
                    roomType = Room.RoomType.DOUBLE;
                    break;
                case "3":
                    roomType = Room.RoomType.SUITE;
                    break;
                default:
                    System.out.println(ANSI_LIGHT_CYAN+"Invalid choice, please try again."+ANSI_RESET);
                    roomType = null; // Set roomType to null
            }
        } while (roomType == null); // Continue looping until a valid choice is made

        // Create room object and add it to the list
        Room room = new Room(roomNumber, roomCategory, roomType);
        rooms.add(room);

        // Output the information of the added room
        System.out.println(ANSI_LIGHT_CYAN+"Room added: " + room.getRoomNumber()+ANSI_RESET);
    }

    // Method to remove a room
    public void removeRoom(LoginSystem loginSystem) {
        Scanner input = new Scanner(System.in);
        System.out.print(ANSI_LIGHT_CYAN+"Enter the room number to remove: "+ANSI_RESET);
        String roomNumberInput = input.nextLine();

        int roomNumber;
        // Validate room number input
        try {
            roomNumber = Integer.parseInt(roomNumberInput); // Convert input to integer
            if (roomNumber <= 0) {
                System.out.println(ANSI_LIGHT_CYAN + "Invalid room number. Please enter a positive number." + ANSI_RESET);
                return; // Exit the method if the input is invalid
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_LIGHT_CYAN + "Invalid input. Please enter a valid number." + ANSI_RESET);
            return; // Exit the method if the input is not a valid number
        }


        // Find the room and remove it
        Room roomToRemove = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) { // Find the room by room number
                roomToRemove = room;
                break;
            }
        }

        if (roomToRemove != null) {
            // Cancel the room from all users' reservations
            Collection<User> users = loginSystem.getUsers(); // Get all users
            for (User user : users) {
                List<Room> reservations = user.getReservations();
                reservations.removeIf(reservedRoom -> reservedRoom.getRoomNumber() == roomNumber);
            }

            // Remove the room from the room list
            rooms.remove(roomToRemove); // Remove the room from the list
            System.out.println(ANSI_LIGHT_CYAN+"Room " + roomNumber + " has been removed successfully, and all its reservations have been canceled."+ANSI_RESET);
        } else {
            System.out.println(ANSI_LIGHT_CYAN+"Room " + roomNumber + " not found."+ANSI_RESET);
        }
    }

    // Method to view all rooms
    public void viewAllRooms() {
        System.out.println(ANSI_LIGHT_CYAN+"All Rooms Information:"+ANSI_RESET);

        // Check if the rooms list is empty
        if (rooms == null || rooms.isEmpty()) {
            System.out.println(ANSI_LIGHT_CYAN+"No rooms available."+ANSI_RESET);
            return; // If there are no rooms, return immediately
        }

        // Print information for each room
        for (Room room : rooms) {
            String availability = room.isAvailable() ? "Available" : "Booked"; // Determine room availability
            String bookedBy = room.isAvailable() ? "N/A" : room.getUserName(); // Get the name of the person who booked
            int reservedDays = room.isAvailable() ? 0 : room.getNights(); // Get the number of nights reserved

            // Print formatted room information
            System.out.printf(ANSI_LIGHT_CYAN+"Room Number: %d | Type: %s | Price: %.2f | Status: %s | Booked By: %s | Reserved Days: %d%n"+ANSI_RESET,
                    room.getRoomNumber(), room.getRoomType(), room.getPerNightPrice(), availability, bookedBy, reservedDays);
        }
    }

    // Method to check all reservations
    public void checkReservations(LoginSystem L) {
        Collection<User> users = L.getUsers(); //LoginSystem has a method to get the list of users

        // Check if there are any users
        if (users == null || users.isEmpty()) {
            System.out.println(ANSI_LIGHT_CYAN+"No users found."+ANSI_RESET);
            return; // If no users, return immediately
        }

        System.out.println(ANSI_LIGHT_CYAN+"Current Reservations:"+ANSI_RESET);
        // Iterate through each user to check their reservations
        for (User user : users) {
            List<Room> reservations = user.getReservations();
            if (reservations.isEmpty()) {
                System.out.println(ANSI_LIGHT_CYAN+user.getUsername() + " has no reservations."+ANSI_RESET); // Notify if user has no reservations
            } else {
                System.out.println(ANSI_LIGHT_CYAN+user.getUsername() + "'s Reservations:"+ANSI_RESET); // Print user's reservations
                for (Room room : reservations) {
                    System.out.println(ANSI_LIGHT_CYAN+room.toString()+ANSI_RESET); // Use Room class's toString method to display room details
                }
            }
        }
    }

    // Method for admin to cancel a reservation
    public void cancelReservationAdmin(LoginSystem L) {
        Collection<User> users = L.getUsers(); // Get all users

        // Check if there are any users
        if (users == null || users.isEmpty()) {
            System.out.println(ANSI_LIGHT_CYAN+"No users found."+ANSI_RESET);
            return; // If no users, return immediately
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the username of the user whose reservation you want to cancel: ");
        String username = scanner.nextLine();
        User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null); // Find user by username

        // Check if the user exists
        if (user == null) {
            System.out.println(ANSI_LIGHT_CYAN+"User not found."+ANSI_RESET);
            return; // If user not found, return
        }

        List<Room> reservations = user.getReservations(); // Get user's reservations
        // Check if the user has any reservations
        if (reservations.isEmpty()) {
            System.out.println(ANSI_LIGHT_CYAN+username + " has no reservations to cancel."+ANSI_RESET);
            return; // If no reservations, return
        }

        System.out.println(ANSI_LIGHT_CYAN+username + "'s Reservations:"+ANSI_RESET);
        // Display all reservations for the user
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println((i + 1) + ": " + reservations.get(i).toString()); // Show all reservations
        }

        System.out.print("Enter the number of the reservation to cancel: ");
        int reservationNumber = scanner.nextInt(); // Get reservation number to cancel

        // Validate reservation number
        if (reservationNumber < 1 || reservationNumber > reservations.size()) {
            System.out.println(ANSI_LIGHT_CYAN+"Invalid reservation number."+ANSI_RESET);
            return; // If invalid, return
        }

        Room roomToCancel = reservations.get(reservationNumber - 1); // Get the room to cancel

        // Set the room as available again
        roomToCancel.setAvailable(true);
        roomToCancel.setUser(null); // Remove the user associated with the room
        reservations.remove(roomToCancel); // Remove the room from the user's reservation list

        System.out.println(ANSI_LIGHT_CYAN+"Reservation for " + roomToCancel.toString() + " has been cancelled."+ANSI_RESET); // Confirm cancellation
    }

    // Main method to run the AdminPanel
    public static void main(String[] args, PanelOperation A, LoginSystem L) {
        Scanner input = new Scanner(System.in); // Create Scanner object for input


        while (true) { // Loop to allow multiple choices
            System.out.println("Welcome to the AdminPanel");
            System.out.println("1. Add a Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Delete a Room");
            System.out.println("4. Check Reservations");
            System.out.println("5. Cancel a Reservation");
            System.out.println("6. Exit");

            System.out.print("Please enter your choice: "); // Prompt user for input
            String choice = input.nextLine(); // Read user's choice

            // Process user choice
            switch (choice) {
                case "1":
                    A.addRoom(); // Call method to add a room
                    break;
                case "2":
                    A.viewAllRooms(); // Call method to view all rooms
                    break;
                case "3":
                    A.removeRoom(L); // Call method to remove a room
                    break;
                case "4":
                    A.checkReservations(L); // Call method to check reservations
                    break;
                case "5":
                    A.cancelReservationAdmin(L); // Call method to cancel a reservation
                    break;
                case "6":
                    System.out.println(ANSI_LIGHT_CYAN+"Launching Main Menu..."+ANSI_RESET);
                    return; // Exit the program
                default:
                    System.out.println(ANSI_LIGHT_CYAN+"Invalid choice, please try again."+ANSI_RESET); // Handle invalid input
            }
        }
    }
}