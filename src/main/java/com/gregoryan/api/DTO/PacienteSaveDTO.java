package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.Endereco;

public record PacienteSaveDTO(String nome, String sobrenome, long telefone, String email, long cpf,Endereco endereco) {
    
}