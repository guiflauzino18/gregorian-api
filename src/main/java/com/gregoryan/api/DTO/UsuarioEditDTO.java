package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.UserRole;

public record UsuarioEditDTO(long id,String nome, String sobrenome, 
                String nascimento, String telefone, String email,
                String endereco, UserRole role, int status) {
    
}
