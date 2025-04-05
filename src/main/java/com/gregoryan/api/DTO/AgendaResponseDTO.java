package com.gregoryan.api.DTO;

import java.util.List;

import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.StatusAgenda;

public record AgendaResponseDTO(
    long id,
    String nome,
    StatusAgenda status,
    String empresaNome,
    long idProfissional,
    String nomeProfissional,
    List<Dias> dias

) {
    
}
