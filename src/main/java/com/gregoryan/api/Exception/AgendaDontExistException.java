package com.gregoryan.api.Exception;

public class AgendaDontExistException extends RuntimeException {

    public AgendaDontExistException(String message){
        super(message);
    }
    
}
