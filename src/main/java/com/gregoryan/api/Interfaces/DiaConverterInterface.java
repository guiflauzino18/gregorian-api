package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.DiaResponseDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface DiaConverterInterface {
    Dias toDia(DiaCadastroDTO dto);
    Dias toDia(DiaEditDTO dto, Usuario usuario);
    DiaResponseDTO toResponseDTO(Dias dia);
    DiaEditDTO toEditDTO(Dias dias);
    DiaEditDTO toEditDTO(DiaCadastroDTO dto, Dias dias);
}
