package nl.conspect.drivedok.exceptions;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super(format("User with id %s not found", id));
    }

    public UserNotFoundException(Long id) {
        super(format("User with id %s not found", id));
    }
}
