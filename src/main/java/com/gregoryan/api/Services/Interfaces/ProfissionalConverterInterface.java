package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;

public interface ProfissionalConverterInterface {
    Profissional toProfissional(ProfissionalCadastroDTO dto, Empresa empresa);
    Profissional toProfissional(ProfissionalEditDTO dto);
    ProfissionalResponseDTO toResponseDTO(Profissional profissional);
}
