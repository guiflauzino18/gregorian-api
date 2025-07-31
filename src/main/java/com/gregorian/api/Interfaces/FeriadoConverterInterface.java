package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.FeriadoCadastroDTO;
import com.gregorian.api.DTO.FeriadoEditDTO;
import com.gregorian.api.DTO.FeriadoResponseDTO;
import com.gregorian.api.Models.Feriado;
import com.gregorian.api.Models.Usuario;

public interface FeriadoConverterInterface {
    Feriado toFeriado(FeriadoCadastroDTO dto);
    Feriado toFeriado(FeriadoEditDTO dto, Usuario usuario);
    FeriadoResponseDTO toResponseDTO(Feriado feriado);
}
