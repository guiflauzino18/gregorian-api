package com.gregorian.api.DTO;

import com.gregorian.api.Models.UserRole;

public record UsuarioCreateDTO(
        String nome,
        String sobrenome,
        String nascimento,
        String telefone,
        String email,
        String login,
        String senha,
        String endereco,
        UserRole role,
        boolean alteraNextLogon) {}
