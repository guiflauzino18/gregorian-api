package com.gregoryan.api.DTO;

import java.util.List;

public record AgendaConfigDTO(List<DiaCadastroDTO> dias, long idAgenda, long idProfissional) {
    
}
