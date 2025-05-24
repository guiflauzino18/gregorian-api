package com.gregoryan.api.DTO;

import java.util.List;

import com.gregoryan.api.Models.Dias;

public record AgendaResponseDTO(
    long id,
    String nome,
    StatusAgendaResponseDTO status,
    String empresaNome,
    long idProfissional,
    String nomeProfissional,
    List<Dias> dias

) {
    
}
