package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;

public interface StatusDiaListInterface {

    Page<StatusDia> list(Empresa empresa, Pageable pageable);
    StatusDia list(long id, Usuario usuario);
    StatusDia list(String nome, Usuario usuario);
}
