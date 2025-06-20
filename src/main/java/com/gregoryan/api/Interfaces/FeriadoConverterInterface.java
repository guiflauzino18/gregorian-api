package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.FeriadoResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Models.Usuario;

public interface FeriadoConverterInterface {
    Feriado toFeriado(FeriadoCadastroDTO dto);
    Feriado toFeriado(FeriadoEditDTO dto, Usuario usuario);
    FeriadoResponseDTO toResponseDTO(Feriado feriado);
}
