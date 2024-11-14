package com.api_park.demo_api_parking.exception;

public class UserNameUniqueViolationException extends RuntimeException{
    public UserNameUniqueViolationException(String msg){
        super(msg);
    }
}
