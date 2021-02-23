package nl.conspect.drivedok.utilities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class BasicLocalDateTimeMapper {
    private static final String FORMAT_NL = "dd-MM-yyyy HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_NL);

    public LocalDateTime asLocalDateTime(String value) {
        return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
    }

    public String asString(LocalDateTime value) {
        return value.format(DATE_TIME_FORMATTER);
    }
}
