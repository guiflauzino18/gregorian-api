package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.Agenda;

public record ProfissionalResponseDTO(
    long id,
    String titulo,
    String registro,
    String nome,
    String sobrenome,
    String login,
    String empresaNome,
    Agenda agenda
) {
    
}
