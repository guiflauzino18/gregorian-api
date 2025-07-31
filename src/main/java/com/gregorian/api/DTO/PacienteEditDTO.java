package com.gregorian.api.DTO;

public record PacienteEditDTO(
    long id,
    String nome,
    String sobrenome,
    long telefone,
    String email,
    long cpf,
    String sexo,
    long idPlanoPaciente
    
) {
    
}
