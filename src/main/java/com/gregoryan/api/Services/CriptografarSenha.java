package com.gregoryan.api.Services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Services.Interfaces.CriptografarSenhaInterface;

@Service
public class CriptografarSenha implements CriptografarSenhaInterface{

    @Override
    public String criptografar(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }
    
}
