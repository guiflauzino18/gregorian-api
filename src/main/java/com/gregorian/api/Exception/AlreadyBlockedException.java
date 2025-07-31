package com.gregorian.api.Exception;

public class AlreadyBlockedException extends RuntimeException {
    public AlreadyBlockedException(String message) {
        super(message);
    }
}
