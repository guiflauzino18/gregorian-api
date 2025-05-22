package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;

import com.gregoryan.api.DTO.DiaResponseDTO;
import com.gregoryan.api.Models.Empresa;

public interface StatusDiaListInterface {
    //findByEmpresa
    Page<DiaResponseDTO> list(Empresa empresa);

    //findById
    DiaResponseDTO list(long id, Empresa empresa);
}
