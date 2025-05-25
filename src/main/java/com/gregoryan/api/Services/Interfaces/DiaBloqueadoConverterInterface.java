package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.DTO.DiaBloqueadoResponseDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;

public interface DiaBloqueadoConverterInterface {
    DiaBloqueado toDiaBloqueado(DiaBloqueadoCadastroDTO dto);
    DiaBloqueado toDiaBloqueado(DiaBloqueadoEditDTO dto, Empresa empresa);
    DiaBloqueadoResponseDTO toResponseDTO(DiaBloqueado diaBloqueado);
}
