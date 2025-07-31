package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.StatusDiaCadastroDTO;
import com.gregorian.api.DTO.StatusDiaEditDTO;
import com.gregorian.api.DTO.StatusDiaResponseDTO;
import com.gregorian.api.Models.StatusDia;
import com.gregorian.api.Models.Usuario;

public interface StatusDiaConverterInterface {
    StatusDiaResponseDTO toResponseDTO(StatusDia statusDia);
    StatusDia toStatusDia(StatusDiaCadastroDTO dto);
    StatusDia toStatusDia(StatusDiaEditDTO dto, Usuario usuario);
}
