package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.DiaResponseDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;

public interface DiaConverterInterface {
    Dias toDia(DiaCadastroDTO dto);
    Dias toDia(DiaEditDTO dto, Empresa empresa);
    DiaResponseDTO toResponseDTO(Dias dia);
    DiaEditDTO toEditDTO(Dias dias);
    DiaEditDTO toEditDTO(DiaCadastroDTO dto, Dias dias);
}
