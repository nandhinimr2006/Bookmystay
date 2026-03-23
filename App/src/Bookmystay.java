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

// Booking Queue Manager
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (FIFO)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for "
                + reservation.getGuestName() + " ("
                + reservation.getRoomType() + ")");
    }

    // Display queue
    public void displayQueue() {
        System.out.println("\n===== Booking Request Queue =====");

        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room: " + r.getRoomType());
        }
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add to queue (FIFO order maintained)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display all requests
        bookingQueue.displayQueue();

        // IMPORTANT: No allocation or inventory updates here
    }
}