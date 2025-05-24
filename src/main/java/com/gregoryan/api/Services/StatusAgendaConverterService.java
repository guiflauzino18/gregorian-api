package com.gregoryan.api.Services;

import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusAgendaResponseDTO;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Interfaces.StatusAgendaConverterInterface;

@Service
public class StatusAgendaConverterService implements StatusAgendaConverterInterface{

    @Override
    public StatusAgenda toStatusAgenda(StatusAgendaCadastroDTO dto) {
        StatusAgenda statusAgenda = new StatusAgenda();
        statusAgenda.setNome(dto.nome());

        return statusAgenda;
    }

    @Override
    public StatusAgendaResponseDTO toResponseDTO(StatusAgenda statusAgenda) {
        
        return new StatusAgendaResponseDTO(statusAgenda.getId(), statusAgenda.getNome());
    }
    
}
