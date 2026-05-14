public class Room {
    public enum RoomType {
        SINGLE, DOUBLE, SUITE // Define room types
    }

    public enum Category {
        DELUXE, SUITE, STANDARD // Define room categories
    }

    private int roomNumber; // Room number
    private RoomType roomType; // Room type
    private Category category; // Room category
    private boolean available; // Availability status of the room
    private double price; // Price per night
    private int nights; // Number of nights for which the room is booked
    private User user; // User who booked the room

    // Constructor
    public Room(int roomNumber, Category category, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.category = category; // Assigning the room category
        this.roomType = roomType;
        this.available = true; // Room is available by default
        this.price = calculatePrice(); // Calculate price based on room type
    }

    // Calculate room price based on room type
    private double calculatePrice() {
        switch (roomType) {
            case SINGLE:
                return 500; // Price for Single Room
            case DOUBLE:
                return 800; // Price for Double Room
            case SUITE:
                return 1200; // Price for Suite Room
            default:
                return 0; // Default value, should not reach here
        }
    }

    // Get final price considering discounts for longer stays
    public double getFinalPrice() {
        price = calculatePrice(); // Update price based on room type
        if (nights >= 7) {
            price = price * 0.8; // Apply a 20% discount for stays of 7 nights or more
        }
        if (category == Category.DELUXE) {
            return price * 3 * nights; // Price calculation for Deluxe Room
        } else if (category == Category.SUITE) {
            return price * 1.5 * nights; // Price calculation for Suite Room
        } else {
            return price * nights; // Price calculation for Standard Room
        }
    }

    // Get price per night
    public double getPerNightPrice() {
        price = calculatePrice(); // Update price based on room type

        if (category == Category.DELUXE) {
            return price * 3; // Price per night for Deluxe Room
        } else if (category == Category.SUITE) {
            return price * 1.5; // Price per night for Suite Room
        } else {
            return price; // Price per night for Standard Room
        }
    }

    // Get room number
    public int getRoomNumber() {
        return roomNumber;
    }

    // Set the number of nights for the booking
    public void setNights(int nights) {
        this.nights = nights; // Set the number of nights for the stay
    }

    // Get the number of nights booked
    public int getNights() {
        return nights;
    }

    // Get room category
    public Category getCategory() {
        return category;
    }

    // Get room type
    public RoomType getRoomType() {
        return roomType;
    }

    // Check if the room is available
    public boolean isAvailable() {
        return available;
    }

    // Set the availability status of the room
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Set the user who booked the room
    public void setUser(User user) {
        this.user = user;
    }

    // Get the username of the user who booked the room
    public String getUserName() {
        return user.getUsername();
    }

    // Get information about the room in string format
    @Override
    public String toString() {
        if (user != null) {
            return "Room Number: " + roomNumber +
                    ", Category: " + category +
                    ", Type: " + roomType +
                    ", Price: RMB " + getPerNightPrice() +
                    ", Available: " + (available ? "Yes" : "No") +
                    ", Booked by: " + getUserName() +
                    ", Reserved Nights: " + nights;
        } else {
            return "Room Number: " + roomNumber +
                    ", Category: " + category +
                    ", Type: " + roomType +
                    ", Price: RMB " + getPerNightPrice() +
                    ", Available: " + (available ? "Yes" : "No") +
                    ", Booked by: None" +
                    ", Reserved Nights: " + nights;
        }
    }
}