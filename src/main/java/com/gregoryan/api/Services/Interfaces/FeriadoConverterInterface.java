package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.FeriadoResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;

public interface FeriadoConverterInterface {
    Feriado toFeriado(FeriadoCadastroDTO dto);
    Feriado toFeriado(FeriadoEditDTO dto, Empresa empresa);
    FeriadoResponseDTO toResponseDTO(Feriado feriado);
}
