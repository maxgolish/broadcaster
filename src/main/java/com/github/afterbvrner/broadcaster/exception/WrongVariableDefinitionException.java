package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongVariableDefinitionException extends RuntimeException {
    public WrongVariableDefinitionException() {
    }

    public WrongVariableDefinitionException(String message) {
        super(message);
    }
}
