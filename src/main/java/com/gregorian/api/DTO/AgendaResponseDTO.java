package com.gregorian.api.DTO;

import java.util.List;

public record AgendaResponseDTO(
    long id,
    String nome,
    StatusAgendaResponseDTO status,
    String empresaNome,
    long idProfissional,
    String nomeProfissional,
    List<DiaResponseDTO> dias

) {
    
}
