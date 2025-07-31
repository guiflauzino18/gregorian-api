package com.gregorian.api.Exception;

public class ConflictException extends RuntimeException{
    
    public ConflictException(String message){
        super(message);
    }
}
