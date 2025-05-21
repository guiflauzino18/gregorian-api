package com.gregoryan.api.DTO;

import java.util.List;

public record AgendaReplicaDiaDTO(
    long idOrigemDia,
    List<String> alvoDias
) {
    
}
