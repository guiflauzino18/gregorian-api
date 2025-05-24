package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.DTO.StatusHoraResponseDTO;
import com.gregoryan.api.Models.StatusHora;

public interface StatusHoraConverterInterface {
    StatusHora toStatusHora(StatusHoraCadastroDTO dto);
    StatusHoraResponseDTO toResponseDTO(StatusHora statusHora);
}
