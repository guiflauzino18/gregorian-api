package com.gregoryan.api.DTO;

public record AgendamentoCreateDTO(
    long profissional,
    long paciente,
    String data,
    String hora,
    long statusAgendamento
) {
    
}
