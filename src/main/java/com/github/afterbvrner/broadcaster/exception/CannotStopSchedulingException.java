package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CannotStopSchedulingException extends RuntimeException {
    public CannotStopSchedulingException() {
        super("Cannot stop scheduling message");
    }

    public CannotStopSchedulingException(UUID id) {
        super("Cannot stop scheduling message, id: " + id);
    }
}
