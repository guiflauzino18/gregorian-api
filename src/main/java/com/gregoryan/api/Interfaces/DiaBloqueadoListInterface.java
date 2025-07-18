package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;

public interface DiaBloqueadoListInterface {
    DiaBloqueado list(long id, Usuario usuario);
    Page<DiaBloqueado> list(Empresa empresa, Pageable pageable);
}
