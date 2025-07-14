package com.gregoryan.api.Exception;

public class EntityDontExistException extends RuntimeException{
    
    public EntityDontExistException(String message){
        super(message);
    }
}
