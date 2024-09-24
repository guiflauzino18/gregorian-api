package com.gregoryan.api.DTO;

import java.util.List;

public record AgendaCadastroDTO(String nome, int profissionalID, List<String> dias) {
    
}
