package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.UserRole;

public record UsuarioCadastroDTO(String nome, String sobrenome, 
                String nascimento, String telefone, String email,
                String login, String senha, String endereco, UserRole role) {
    
}
