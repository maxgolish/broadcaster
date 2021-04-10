package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ScheduledTaskNotFound extends RuntimeException {
    public ScheduledTaskNotFound() {
        super("Scheduled task not found");
    }

    public ScheduledTaskNotFound(UUID id) {
        super("Scheduled task " + id + " not found");
    }
}
