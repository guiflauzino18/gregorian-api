package com.gregorian.api.Exception;

public class EntityDontExistException extends RuntimeException{
    
    public EntityDontExistException(String message){
        super(message);
    }
}
