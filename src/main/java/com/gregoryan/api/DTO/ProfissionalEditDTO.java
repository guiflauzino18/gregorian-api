package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.Profissional.StatusProfissional;

public record ProfissionalEditDTO(long id, String titulo, String registro, long idAgenda, StatusProfissional status) {
    
}
