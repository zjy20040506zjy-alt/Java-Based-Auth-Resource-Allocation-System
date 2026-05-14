import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomGenerator {
    public static List<Room> generateRooms() {
        List<Room> rooms = new ArrayList<>(); // Initialize the list of rooms
        Random random = new Random(); // Define the random number generator

        for (int i = 1; i <= 5; i++) { // Create 5 rooms (change to 100 if needed)
            int roomType = random.nextInt(3); // Randomly select room type, 0-2
            int categoryType = random.nextInt(3); // Randomly select room category, 0-2
            Room.Category category;
            Room.RoomType roomTypeEnum;

            // Choose the room category based on the random number
            if (categoryType == 0) {
                category = Room.Category.DELUXE; // Deluxe room
            } else if (categoryType == 1) {
                category = Room.Category.SUITE; // Suite room
            } else {
                category = Room.Category.STANDARD; // Standard room
            }

            // Choose the room type based on the random number
            if (roomType == 0) {
                roomTypeEnum = Room.RoomType.SUITE; // Assuming deluxe room is of suite type
            } else if (roomType == 1) {
                roomTypeEnum = Room.RoomType.DOUBLE; // Assuming suite room is of double type
            } else {
                roomTypeEnum = Room.RoomType.SINGLE; // Assuming standard room is of single type
            }

            // Create the room object and add it to the list
            Room room = new Room(i, category, roomTypeEnum);
            rooms.add(room);
        }

        return rooms; // Return the list of generated rooms
    }
}