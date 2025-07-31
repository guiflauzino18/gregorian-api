package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.DiaCadastroDTO;
import com.gregorian.api.DTO.DiaEditDTO;
import com.gregorian.api.DTO.DiaResponseDTO;
import com.gregorian.api.Models.Dias;
import com.gregorian.api.Models.Usuario;

public interface DiaConverterInterface {
    Dias toDia(DiaCadastroDTO dto);
    Dias toDia(DiaEditDTO dto, Usuario usuario);
    DiaResponseDTO toResponseDTO(Dias dia);
    DiaEditDTO toEditDTO(Dias dias);
    DiaEditDTO toEditDTO(DiaCadastroDTO dto, Dias dias);
}
