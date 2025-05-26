package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Models.Agenda;

public interface AgendaConverterInterface {
    
    Agenda toAgenda(AgendaCadastroDTO dto);
    AgendaResponseDTO toResponseDTO(Agenda agenda);
}
