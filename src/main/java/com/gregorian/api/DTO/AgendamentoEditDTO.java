package com.gregorian.api.DTO;

public record AgendamentoEditDTO(
    long id,
    long idProfissional,
    long idPaciente,
    long idStatus,
    String data,
    String hora
) {
    
}
