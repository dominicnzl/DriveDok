package nl.conspect.drivedok.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super(format("User with id %s not found", id));
    }

    public UserNotFoundException(Long id) {
        super(format("User with id %s not found", id));
    }
}
