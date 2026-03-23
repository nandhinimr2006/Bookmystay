import java.util.*;

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

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Service (Allocation + Uniqueness)
class BookingService {

    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService() {
        allocatedRooms = new HashMap<>();
    }

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        System.out.println("===== Processing Bookings =====\n");

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                // Generate unique ID
                String roomId = generateRoomId(type);

                allocatedRooms.putIfAbsent(type, new HashSet<>());

                // Ensure uniqueness
                if (!allocatedRooms.get(type).contains(roomId)) {

                    allocatedRooms.get(type).add(roomId);

                    // Update inventory immediately
                    inventory.decreaseAvailability(type);

                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + r.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Allocated Room ID: " + roomId + "\n");
                }

            } else {
                System.out.println("Booking Failed for " + r.getGuestName() +
                        " (No rooms available for " + type + ")\n");
            }
        }
    }

    private String generateRoomId(String type) {
        return type.substring(0, 2).toUpperCase() + "-"
                + UUID.randomUUID().toString().substring(0, 4);
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        // Create queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 2);
        inventory.addRoom("Suite Room", 1);

        // Process bookings
        BookingService bookingService = new BookingService();
        bookingService.processBookings(queue, inventory);
    }
}