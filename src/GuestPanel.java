import java.util.List;
import java.util.Scanner;

public class GuestPanel extends PanelOperation {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_LIGHT_CYAN = "\u001B[96m";

    public void viewAvailableRooms() {
        boolean foundAvailableRoom = false; // Flag variable to check if an available room is found

        System.out.println(ANSI_LIGHT_CYAN+"Available Rooms:"+ANSI_RESET);
        for (Room room : rooms) {
            if (room.isAvailable()) { // the Room class has an isAvailable() method
                System.out.println(ANSI_LIGHT_CYAN+"Room Number: " + room.getRoomNumber() +
                        ", Category: " + room.getCategory() +
                        ", Type: " + room.getRoomType() +
                        ", Per Night Price: " + room.getPerNightPrice()+ANSI_RESET);
                foundAvailableRoom = true; // Found an available room
            }
        }

        if (!foundAvailableRoom) {
            System.out.println(ANSI_LIGHT_CYAN+"No rooms are currently available for booking."+ANSI_RESET);
        }
    }

    public void bookARoom(User user) {
        Scanner input = new Scanner(System.in);
        viewAvailableRooms(); // Use the existing method to display available rooms

        System.out.print("Enter the room number you want to book: ");
        String roomNumberInput = input.nextLine();

        System.out.print("Enter how many nights you want to stay: ");
        String nightsInput = input.nextLine();

        // Validate and parse room number
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberInput);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_LIGHT_CYAN + "Invalid room number. Please enter a valid integer." + ANSI_RESET);
            return;
        }

        // Validate and parse number of nights
        int nights;
        try {
            nights = Integer.parseInt(nightsInput);
            if (nights <= 0) {
                System.out.println(ANSI_LIGHT_CYAN + "Invalid number of nights. Please enter a positive number." + ANSI_RESET);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_LIGHT_CYAN + "Invalid number of nights. Please enter a valid integer." + ANSI_RESET);
            return;
        }

        // Find the room selected by the user
        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }

        // If the room is found and available
        if (selectedRoom != null) {
            // Set the room to unavailable
            selectedRoom.setAvailable(false);
            selectedRoom.setNights(nights); // Room class has a setNights method
            selectedRoom.setUser(user);
            user.addReservation(selectedRoom); // User class has an addReservation method

            System.out.println(ANSI_LIGHT_CYAN + "Room booked successfully!");
            System.out.println("Booking Details: ");
            System.out.println(selectedRoom.toString());
            System.out.println("Total Price: RMB " + selectedRoom.getFinalPrice() + ANSI_RESET);
        } else {
            System.out.println(ANSI_LIGHT_CYAN + "Room not available or does not exist. Please try again." + ANSI_RESET);
        }
    }


    public void ViewYourReservations(User user) {
        System.out.println(ANSI_LIGHT_CYAN+"Current Reservations:");
        List<Room> reservations = user.getReservations();
        if (reservations.isEmpty()) {
            System.out.println(ANSI_LIGHT_CYAN+user.getUsername() + " has no reservations."+ANSI_RESET);
        } else {
            System.out.println(ANSI_LIGHT_CYAN+user.getUsername() + "'s Reservations:"+ANSI_RESET);
            for (Room room : reservations) {
                System.out.println(ANSI_LIGHT_CYAN+room.toString()+ANSI_RESET); // Use Room class's toString method
            }
        }
    }

    public void cancelReservationGuest(User user) {
        List<Room> reservations = user.getReservations(); // Get the user's reservation list

        if (reservations.isEmpty()) {
            System.out.println(ANSI_LIGHT_CYAN+user.getUsername() + " has no reservations to cancel."+ANSI_RESET);
            return;
        }

        System.out.println(ANSI_LIGHT_CYAN+user.getUsername() + "'s Reservations:"+ANSI_RESET);
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println(ANSI_LIGHT_CYAN+(i + 1) + ": " + reservations.get(i).toString()+ANSI_RESET); // Display all reservations
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print(ANSI_LIGHT_CYAN+"Enter the number of the reservation to cancel: "+ANSI_RESET);
        String reservationNumberInput = scanner.nextLine();

        // Validate the input to ensure it is a valid number
        int reservationNumber;
        try {
            reservationNumber = Integer.parseInt(reservationNumberInput); // Convert input to integer
        } catch (NumberFormatException e) {
            System.out.println(ANSI_LIGHT_CYAN + "Invalid input. Please enter a valid number." + ANSI_RESET);
            return;
        }

        // Check if the entered reservation number is valid
        if (reservationNumber < 1 || reservationNumber > reservations.size()) {
            System.out.println(ANSI_LIGHT_CYAN+"Invalid reservation number."+ANSI_RESET);
            return;
        }

        Room roomToCancel = reservations.get(reservationNumber - 1);

        // Set the room as available again
        roomToCancel.setAvailable(true);
        roomToCancel.setUser(null); // Remove the user associated with the room
        reservations.remove(roomToCancel); // Remove the room from the user's reservation list

        System.out.println(ANSI_LIGHT_CYAN+"Reservation for " + roomToCancel.toString() + " has been cancelled."+ANSI_RESET);
    }


    public static User logMenu(LoginSystem L) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Guest Login System");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    return L.login(); // Exit this method after logging in
                case "2":
                    L.register();
                    break;
                case "3":
                    System.out.println(ANSI_LIGHT_CYAN+"Exiting."+ANSI_RESET);
                    return null;
                default:
                    System.out.println(ANSI_LIGHT_CYAN+"Invalid choice, please try again."+ANSI_RESET);
            }
        }
    }

    public static void main(String[] args, PanelOperation A, LoginSystem L) {
        User user = GuestPanel.logMenu(L);
        if (user == null) { return; }
        Scanner input = new Scanner(System.in); // Create Scanner object for input


        while (true) { // Loop to allow multiple choices
            System.out.println("Welcome to the GuestPanel");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Your Reservations");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. Exit");

            System.out.print("Please enter your choice: "); // Prompt user for input
            String choice = input.nextLine(); // Read user's input as an integer

            switch (choice) {
                case "1":
                    A.viewAvailableRooms();
                    break;
                case "2":
                    A.bookARoom(user);
                    break;
                case "3":
                    A.ViewYourReservations(user);
                    break;
                case "4":
                    A.cancelReservationGuest(user);
                    break;
                case "5":
                    System.out.println(ANSI_LIGHT_CYAN+"Launching Main Menu..."+ANSI_RESET);
                    return; // Exit the program
                default:
                    System.out.println(ANSI_LIGHT_CYAN+"Invalid choice, please try again."+ANSI_RESET);
            }
        }
    }
}