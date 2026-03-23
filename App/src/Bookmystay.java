import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Class
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1); // -1 = invalid type
    }

    public void decreaseAvailability(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, available - 1);
    }
}

// Validator Class
class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate room type
        if (inventory.getAvailability(r.getRoomType()) == -1) {
            throw new InvalidBookingException("Invalid Room Type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.getRoomType());
        }

        // Validate guest name
        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }
    }
}

// Booking Service
class BookingService {

    public void confirmBooking(Reservation r, RoomInventory inventory) {
        try {
            // Fail-fast validation
            BookingValidator.validate(r, inventory);

            // Only if valid → update inventory
            inventory.decreaseAvailability(r.getRoomType());

            System.out.println("Booking Confirmed for " + r.getGuestName()
                    + " (" + r.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 1);
        inventory.addRoom("Suite Room", 0);

        // Booking service
        BookingService service = new BookingService();

        // Test cases
        Reservation r1 = new Reservation("Alice", "Single Room");   // valid
        Reservation r2 = new Reservation("Bob", "Suite Room");      // no availability
        Reservation r3 = new Reservation("Charlie", "Deluxe Room"); // invalid type
        Reservation r4 = new Reservation("", "Single Room");        // invalid name

        service.confirmBooking(r1, inventory);
        service.confirmBooking(r2, inventory);
        service.confirmBooking(r3, inventory);
        service.confirmBooking(r4, inventory);
    }
}
