package GUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public static String getCurrentDateAsString() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
}