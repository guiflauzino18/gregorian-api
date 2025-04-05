package com.gregoryan.api.DTO;

import java.util.Calendar;

import com.gregoryan.api.Models.UserRole;

public record UsuarioResponseDTO(
    long id,
    String nome,
    String sobrenome,
    Calendar nascimento,
    String telefone,
    String email,
    String login,
    String endereco,
    int status,
    boolean alteraNextLogon,
    UserRole role,
    Calendar dataRegistro,
    String empresaNome

) {
    
}
