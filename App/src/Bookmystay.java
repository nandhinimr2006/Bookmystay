import java.io.*;
import java.util.*;

// Reservation Class (Serializable)
class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// System State Class (Serializable)
class SystemState implements Serializable {
    HashMap<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(HashMap<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save system state
    public static void saveState(SystemState state) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load system state
    public static SystemState loadState() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        HashMap<String, Integer> inventory;
        List<Reservation> bookings;

        // Load previous state
        SystemState state = PersistenceService.loadState();

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("\nRecovered Data:");
        } else {
            // Initialize new system
            inventory = new HashMap<>();
            bookings = new ArrayList<>();

            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings.add(new Reservation("R101", "Alice", "Single Room"));
            bookings.add(new Reservation("R102", "Bob", "Double Room"));

            System.out.println("\nNew System Initialized:");
        }

        // Display inventory
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }

        // Display bookings
        System.out.println("\nBookings:");
        for (Reservation r : bookings) {
            System.out.println(r.getReservationId() + " - "
                    + r.getGuestName() + " (" + r.getRoomType() + ")");
        }

        // Save state before exit
        SystemState newState = new SystemState(inventory, bookings);
        PersistenceService.saveState(newState);
    }
}