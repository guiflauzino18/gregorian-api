package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;

public interface StatusDiaListInterface {

    Page<StatusDia> list(Empresa empresa, Pageable pageable);
    StatusDia list(long id, Empresa empresa);
    StatusDia list(String nome, Empresa empresa);
}
