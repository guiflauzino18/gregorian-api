package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.Endereco;

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