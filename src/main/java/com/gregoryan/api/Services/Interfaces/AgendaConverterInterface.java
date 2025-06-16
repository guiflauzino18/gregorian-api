package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;

public interface AgendaConverterInterface {
    
    Agenda toAgenda(AgendaCadastroDTO dto);
    Agenda toAgenda(AgendaEditDTO dto, Empresa empresa);
    AgendaResponseDTO toResponseDTO(Agenda agenda);

}
