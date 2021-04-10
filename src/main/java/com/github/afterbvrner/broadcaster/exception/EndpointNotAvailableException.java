package com.github.afterbvrner.broadcaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class EndpointNotAvailableException extends RuntimeException {
    public EndpointNotAvailableException() {
        super("Endpoint not available");
    }

    public EndpointNotAvailableException(String url) {
        super("Endpoint " + url + " not available");
    }

    public EndpointNotAvailableException(String url, Throwable cause) {
        super("Endpoint " + url + " not available, cause: " + cause.getMessage());
    }
}
