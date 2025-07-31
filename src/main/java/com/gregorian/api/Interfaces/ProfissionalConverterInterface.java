package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.ProfissionalCadastroDTO;
import com.gregorian.api.DTO.ProfissionalEditDTO;
import com.gregorian.api.DTO.ProfissionalListDTO;
import com.gregorian.api.DTO.ProfissionalResponseDTO;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.Usuario;

public interface ProfissionalConverterInterface {
    Profissional toProfissional(ProfissionalCadastroDTO dto, Usuario usuario);
    Profissional toProfissional(ProfissionalEditDTO dto, Usuario usuario);
    ProfissionalResponseDTO toResponseDTO(Profissional profissional);
    ProfissionalListDTO toListDTO(Profissional profissional);
}
