package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoObjectInRepoException extends RuntimeException{
    public NoObjectInRepoException() {
    }

    public NoObjectInRepoException(String message) {
        super(message);
    }

    public NoObjectInRepoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoObjectInRepoException(Throwable cause) {
        super(cause);
    }
}
