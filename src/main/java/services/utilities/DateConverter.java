package services.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConverter {
    public static String formatDate(LocalDateTime date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);
            return date.format(formatter);  // Return the formatted date string
        }
        return "N/A";  // Return a default value if the date is null
    }

    // Overloaded method for Timestamp if needed
    public static String formatDate(java.sql.Timestamp timestamp) {
        if (timestamp != null) {
            LocalDateTime date = timestamp.toLocalDateTime();  // Convert Timestamp to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);
            return date.format(formatter);  // Return the formatted date string
        }
        return "N/A";  // Return a default value if the timestamp is null
    }

}
