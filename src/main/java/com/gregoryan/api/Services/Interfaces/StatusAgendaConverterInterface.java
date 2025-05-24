package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusAgendaResponseDTO;
import com.gregoryan.api.Models.StatusAgenda;

public interface StatusAgendaConverterInterface {
    StatusAgenda toStatusAgenda(StatusAgendaCadastroDTO dto);
    StatusAgendaResponseDTO toResponseDTO(StatusAgenda statusAgenda);
}
