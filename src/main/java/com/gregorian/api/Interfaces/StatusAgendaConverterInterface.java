package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.StatusAgendaCadastroDTO;
import com.gregorian.api.DTO.StatusAgendaResponseDTO;
import com.gregorian.api.Models.StatusAgenda;

public interface StatusAgendaConverterInterface {
    StatusAgenda toStatusAgenda(StatusAgendaCadastroDTO dto);
    StatusAgendaResponseDTO toResponseDTO(StatusAgenda statusAgenda);
}
