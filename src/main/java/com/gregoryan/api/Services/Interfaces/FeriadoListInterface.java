package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;

public interface FeriadoListInterface {
    Feriado list(long id, Empresa empresa);
    Page<Feriado> list(Empresa empresa, Pageable pageable);
}
