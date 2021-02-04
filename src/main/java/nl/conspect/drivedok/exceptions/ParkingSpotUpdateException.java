package nl.conspect.drivedok.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ParkingSpotUpdateException extends RuntimeException {

    public ParkingSpotUpdateException() {
        super("Your Parkingspot was unable to update properly");
    }
}
