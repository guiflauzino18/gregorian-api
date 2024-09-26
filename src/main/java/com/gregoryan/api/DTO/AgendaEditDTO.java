package com.gregoryan.api.DTO;

import java.util.List;

public record AgendaEditDTO(long idAgenda, String nome, long idStatusAgenda, 
                            List<DiaEditDTO> diaDTO) {
    
}