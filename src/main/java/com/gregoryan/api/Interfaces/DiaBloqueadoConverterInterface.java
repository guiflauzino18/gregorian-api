package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.DTO.DiaBloqueadoResponseDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface DiaBloqueadoConverterInterface {
    DiaBloqueado toDiaBloqueado(DiaBloqueadoCadastroDTO dto);
    DiaBloqueado toDiaBloqueado(DiaBloqueadoEditDTO dto, Usuario usuario);
    DiaBloqueadoResponseDTO toResponseDTO(DiaBloqueado diaBloqueado);
}
