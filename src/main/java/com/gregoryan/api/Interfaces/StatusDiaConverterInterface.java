package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.Usuario;

public interface StatusDiaConverterInterface {
    StatusDiaResponseDTO toResponseDTO(StatusDia statusDia);
    StatusDia toStatusDia(StatusDiaCadastroDTO dto);
    StatusDia toStatusDia(StatusDiaEditDTO dto, Usuario usuario);
}
