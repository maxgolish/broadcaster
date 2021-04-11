package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnusedVariablesException extends RuntimeException {
    public UnusedVariablesException() {
        super("Unused variables left in template");
    }

    public UnusedVariablesException(List<String> variables) {
        super("Unused variables left in template: " + String.join(", ", variables));
    }
}
