package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Models.Empresa;

public interface AgendaListInterface {

    AgendaResponseDTO list(long id, Empresa empresa);
    Page<AgendaResponseDTO> list(Empresa empresa, Pageable pageable);

}
