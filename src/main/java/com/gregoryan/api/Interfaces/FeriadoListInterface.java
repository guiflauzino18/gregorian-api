package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;

public interface FeriadoListInterface {
    Feriado list(long id, Usuario usuario);
    Page<Feriado> list(Empresa empresa, Pageable pageable);
}
