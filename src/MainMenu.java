import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_LIGHT_CYAN = "\u001B[96m";
    public static void main(String[] args) {
        PanelOperation A; // Declare a variable of type PanelOperation
        List<Room> generatedRooms = RoomGenerator.generateRooms(); // Generate rooms

        // Add the generated rooms to the room list in PanelOperation
        A=new AdminPanel();
        A.getRooms().addAll(generatedRooms);

        LoginSystem L = new LoginSystem(); // Create a new instance of LoginSystem
        Scanner input = new Scanner(System.in); // Create a Scanner object for reading input

        String choice = "0"; // Variable to store user choice

        while (true) { // Loop to allow the user to make repeated selections
            System.out.println("Welcome to the Hotel Reservation System");
            System.out.println("1. Guest Panel");
            System.out.println("2. Admin Panel");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice: "); // Prompt the user for input

            choice = input.nextLine();// Read the user's input as an integer
            switch (choice) {
                case "1":
                    System.out.println(ANSI_LIGHT_CYAN+"Launching Guest Panel..."+ANSI_RESET);
                    // Create a new GuestPanel and start its main method
                    A = new GuestPanel();
                    GuestPanel.main(args, A, L);
                    break;
                case "2":
                    System.out.println(ANSI_LIGHT_CYAN+"Launching Admin Panel..."+ANSI_RESET);
                    // Create a new AdminPanel and start its main method
                    A = new AdminPanel();
                    AdminPanel.main(args, A, L);
                    break;
                case "3":
                    System.out.println(ANSI_LIGHT_CYAN+"Thank you for using the system."+ANSI_RESET);
                    input.close(); // Close the Scanner
                    return; // Exit the program
                default:
                    System.out.println(ANSI_LIGHT_CYAN+"Invalid choice, please try again."+ANSI_RESET); // Prompt for a valid choice
            }
        }
    }
}