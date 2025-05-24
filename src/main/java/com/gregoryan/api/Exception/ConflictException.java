package com.gregoryan.api.Exception;

public class ConflictException extends RuntimeException{
    
    public ConflictException(String message){
        super(message);
    }
}
