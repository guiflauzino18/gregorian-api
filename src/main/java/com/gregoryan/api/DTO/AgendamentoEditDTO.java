package com.gregoryan.api.DTO;

public record AgendamentoEditDTO(
    long id,
    long idProfissional,
    long idPaciente,
    long idStatus,
    String data,
    String hora
) {
    
}
