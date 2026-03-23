
import java.util.*;

// Reservation Class
class Reservation {
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

// Booking History (stores confirmed bookings)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("===== Booking History =====\n");

        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.getReservationId());
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + r.getRoomType());
            System.out.println();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        HashMap<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            summary.put(type, summary.getOrDefault(type, 0) + 1);
        }

        System.out.println("===== Booking Summary =====\n");

        for (String type : summary.keySet()) {
            System.out.println(type + " Bookings: " + summary.get(type));
        }
    }
}

// Main Class (as required)
public class Bookmystay {

    public static void main(String[] args) {

        // Booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R101", "Alice", "Single Room");
        Reservation r2 = new Reservation("R102", "Bob", "Double Room");
        Reservation r3 = new Reservation("R103", "Charlie", "Single Room");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Display booking history
        reportService.displayAllBookings(history.getAllReservations());

        // Display summary report
        reportService.generateSummary(history.getAllReservations());
    }
}