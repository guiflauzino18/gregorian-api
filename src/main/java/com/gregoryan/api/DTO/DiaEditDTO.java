package com.gregoryan.api.DTO;

import java.util.List;

public record DiaEditDTO(long idDia, long intervaloSessaoInMinutes, 
long duracaoSessaoInMinutes, long idStatusDIa, 
String inicio, String fim, List<HorasEditDTO> horaDTO) {
    
}
