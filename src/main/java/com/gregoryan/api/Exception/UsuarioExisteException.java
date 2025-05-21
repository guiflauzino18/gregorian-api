package com.gregoryan.api.Exception;

public class UsuarioExisteException extends RuntimeException {

    public UsuarioExisteException(String message){
        super(message);
    }

}
