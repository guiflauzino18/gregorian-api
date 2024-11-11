package com.gregoryan.api.DTO;

public record AgendamentoCadastroDTO(
    long profissional,
    long paciente,
    String data,
    String hora,
    long statusAgendamento
) {
    
}
