package mailru.nastasiachernega.tests.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy");
        return LocalDate.now().format(formatter);
    }
}
