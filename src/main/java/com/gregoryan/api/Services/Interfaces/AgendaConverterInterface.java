package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaConfigDTO;
import com.gregoryan.api.Models.Agenda;

public interface AgendaConverterInterface {
    
    Agenda toAgenda(AgendaCadastroDTO dto);
    Agenda toAgenda(AgendaConfigDTO dto);
}
