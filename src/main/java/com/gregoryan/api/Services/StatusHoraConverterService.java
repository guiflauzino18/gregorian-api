package com.gregoryan.api.Services;

import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.DTO.StatusHoraResponseDTO;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Interfaces.StatusHoraConverterInterface;

@Service
public class StatusHoraConverterService implements StatusHoraConverterInterface{

    @Override
    public StatusHora toStatusHora(StatusHoraCadastroDTO dto) {
        StatusHora statusHora = new StatusHora();
        statusHora.setNome(dto.nome());

        return statusHora;
    }

    @Override
    public StatusHoraResponseDTO toResponseDTO(StatusHora statusHora) {
        StatusHoraResponseDTO dto = new StatusHoraResponseDTO(
            statusHora.getId(),
            statusHora.getNome());

            return dto;
    }
    

}
