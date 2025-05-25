package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;

public interface StatusDiaListInterface {
    //findByEmpresa
    Page<StatusDia> list(Empresa empresa, Pageable pageable);

    //findById
    StatusDia list(long id, Empresa empresa);
}
