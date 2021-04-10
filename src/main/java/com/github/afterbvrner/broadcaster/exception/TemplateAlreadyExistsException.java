package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TemplateAlreadyExistsException extends RuntimeException {
    public TemplateAlreadyExistsException() {
        super("Template with same id already exists");
    }

    public TemplateAlreadyExistsException(String id) {
        super("Template \"" + id + "\" already exists");
    }
}
