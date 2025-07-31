package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.StatusHoraCadastroDTO;
import com.gregorian.api.DTO.StatusHoraResponseDTO;
import com.gregorian.api.Models.StatusHora;

public interface StatusHoraConverterInterface {
    StatusHora toStatusHora(StatusHoraCadastroDTO dto);
    StatusHoraResponseDTO toResponseDTO(StatusHora statusHora);
}
