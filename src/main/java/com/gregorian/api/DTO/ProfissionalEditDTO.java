package com.gregorian.api.DTO;

import com.gregorian.api.Models.Profissional.StatusProfissional;

public record ProfissionalEditDTO(long id, String titulo, String registro, long idAgenda, StatusProfissional status) {
    
}
