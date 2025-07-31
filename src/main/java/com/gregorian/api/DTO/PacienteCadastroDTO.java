package com.gregorian.api.DTO;

import com.gregorian.api.Models.Endereco;

public record PacienteCadastroDTO(
    String nome, 
    String sobrenome, 
    long telefone, 
    String email, 
    long cpf,
    String sexo,
    long idPlanoPaciente,
    Endereco endereco) {
    
}