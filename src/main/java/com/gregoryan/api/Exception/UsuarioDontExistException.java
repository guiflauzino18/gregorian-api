package com.gregoryan.api.Exception;

public class UsuarioDontExistException extends RuntimeException {

    public UsuarioDontExistException(String message){
        super(message);
    }
    
}
