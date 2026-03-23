import java.util.*;

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
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

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Available: " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private HashMap<String, Reservation> history;

    public BookingHistory() {
        history = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return history.get(id);
    }

    public void removeReservation(String id) {
        history.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack;

    public CancellationService() {
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        Reservation r = history.getReservation(reservationId);

        // Validate
        if (r == null) {
            System.out.println("Cancellation Failed: Invalid Reservation ID");
            return;
        }

        // Push to rollback stack (LIFO)
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increaseAvailability(r.getRoomType());

        // Remove from history
        history.removeReservation(reservationId);

        System.out.println("Booking Cancelled Successfully!");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + r.getRoomId());
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 1);

        // Booking history
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R101", "Alice", "Single Room", "SR-001");
        Reservation r2 = new Reservation("R102", "Bob", "Single Room", "SR-002");

        history.addReservation(r1);
        history.addReservation(r2);

        // Cancellation service
        CancellationService service = new CancellationService();

        // Cancel valid booking
        service.cancelBooking("R101", history, inventory);

        // Attempt invalid cancellation
        service.cancelBooking("R999", history, inventory);

        // Show inventory
        inventory.displayInventory();

        // Show rollback stack
        service.showRollbackStack();
    }
}