package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalListDTO;
import com.gregoryan.api.DTO.ProfissionalResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.Usuario;

public interface ProfissionalConverterInterface {
    Profissional toProfissional(ProfissionalCadastroDTO dto, Usuario usuario);
    Profissional toProfissional(ProfissionalEditDTO dto, Usuario usuario);
    ProfissionalResponseDTO toResponseDTO(Profissional profissional);
    ProfissionalListDTO toListDTO(Profissional profissional);
}
