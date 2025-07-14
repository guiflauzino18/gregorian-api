package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface AgendaConverterInterface {
    
    Agenda toAgenda(AgendaCadastroDTO dto);
    Agenda toAgenda(AgendaEditDTO dto, Usuario usuario);
    AgendaResponseDTO toResponseDTO(Agenda agenda);

}
