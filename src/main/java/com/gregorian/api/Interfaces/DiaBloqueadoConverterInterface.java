package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregorian.api.DTO.DiaBloqueadoEditDTO;
import com.gregorian.api.DTO.DiaBloqueadoResponseDTO;
import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Models.Usuario;

public interface DiaBloqueadoConverterInterface {
    DiaBloqueado toDiaBloqueado(DiaBloqueadoCadastroDTO dto);
    DiaBloqueado toDiaBloqueado(DiaBloqueadoEditDTO dto, Usuario usuario);
    DiaBloqueadoResponseDTO toResponseDTO(DiaBloqueado diaBloqueado);
}
