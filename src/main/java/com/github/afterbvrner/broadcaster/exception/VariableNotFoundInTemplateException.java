package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VariableNotFoundInTemplateException extends RuntimeException {
    public VariableNotFoundInTemplateException() {
        super("Variable not found in template");
    }

    public VariableNotFoundInTemplateException(String variable) {
        super("Variable " + variable + "not found in template");
    }
}
