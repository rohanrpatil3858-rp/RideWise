package com.airtribe.ridewise.exception;

/**
 * Exception thrown when no drivers are available for a ride request
 */
public class NoDriverAvailableException extends Exception {
    public NoDriverAvailableException(String message) {
        super(message);
    }

    public NoDriverAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

