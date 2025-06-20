package com.gregoryan.api.Components;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Interfaces.CriptografarSenhaInterface;

@Component
public class CriptografarSenha implements CriptografarSenhaInterface{

    @Override
    public String criptografar(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }
    
}
