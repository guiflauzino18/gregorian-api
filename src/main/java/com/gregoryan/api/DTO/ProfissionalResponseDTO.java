package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Profissional.StatusProfissional;

public record ProfissionalResponseDTO(
    long id,
    String titulo,
    String registro,
    String nome,
    String sobrenome,
    String login,
    String empresaNome,
    StatusProfissional status,
    Agenda agenda
) {
    
}
