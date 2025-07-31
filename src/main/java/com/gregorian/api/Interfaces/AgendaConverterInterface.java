package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.AgendaCadastroDTO;
import com.gregorian.api.DTO.AgendaEditDTO;
import com.gregorian.api.DTO.AgendaResponseDTO;
import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Models.Usuario;

public interface AgendaConverterInterface {
    
    Agenda toAgenda(AgendaCadastroDTO dto);
    Agenda toAgenda(AgendaEditDTO dto, Usuario usuario);
    AgendaResponseDTO toResponseDTO(Agenda agenda);

}
