package com.api_park.demo_api_parking.exception;

public class CpfUniqueViolationException extends RuntimeException {

    public CpfUniqueViolationException(String message) {
        super(message);
    }

}
