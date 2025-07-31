package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Feriado;

public interface FeriadoListInterface {
    Feriado list(long id, Usuario usuario);
    Page<Feriado> list(Empresa empresa, Pageable pageable);
}
