package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;

public interface DiaBloqueadoListInterface {
    DiaBloqueado list(long id, Empresa empresa);
    Page<DiaBloqueado> list(Empresa empresa, Pageable pageable);
}
