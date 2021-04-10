package com.github.afterbvrner.broadcaster.exception;

import com.github.afterbvrner.broadcaster.model.scheduled.SchedulingType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownSchedulingTypeException extends RuntimeException {
    public UnknownSchedulingTypeException(SchedulingType type) {
        super("Unknown scheduling type " + type);
    }
}
