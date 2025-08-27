package Header.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface FileLogger { // Interface for logging actions in the parking system (like reserving, parking, cancelling, and checking out)

    public static final String LOG_FILE = "log.txt";
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static void log(String action, String details) {
        String timestamp = LocalDateTime.now().format(dtf);
        String entry = String.format("[%s] %s: %s", timestamp, action, details);
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) { // true for append mode
            out.println(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logReserve(String userId, String userName, String spotId) {
        log("RESERVE", "User " + userId + " (" + userName + ") reserved spot " + spotId);
    }

    public static void logCancel(String userId, String userName, String spotId) {
        log("CANCEL", "User " + userId + " (" + userName + ") cancelled reservation for spot " + spotId);
    }

    public static void logPark(String userId, String userName, String spotId) {
        log("PARK", "User " + userId + " (" + userName + ") parked at spot " + spotId);
    }

    public static void logCheckout(String userId, String userName, String spotId) {
        log("CHECKOUT", "User " + userId + " (" + userName + ") checked out from spot " + spotId);
    }
}