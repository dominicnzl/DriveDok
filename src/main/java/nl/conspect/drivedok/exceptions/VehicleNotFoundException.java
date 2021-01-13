package nl.conspect.drivedok.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(String message) {
        super(format("Vehicle with %s was not found.", message));
    }

    public VehicleNotFoundException(Long id) {
        super(format("Vehicle with %s was not found.", id));
    }
}
