import java.util.*;

// Abstract Room Class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

// Concrete Room Classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// Inventory Class (Read-only access used here)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // READ ONLY
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search Service (NO updates allowed)
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("===== Available Rooms =====\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Show only available rooms
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Store rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(single);
        rooms.add(doubleRoom);
        rooms.add(suite);

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 0); // not available
        inventory.addRoom("Suite Room", 2);

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Guest searches rooms (READ ONLY)
        searchService.searchAvailableRooms(rooms, inventory);
    }
}