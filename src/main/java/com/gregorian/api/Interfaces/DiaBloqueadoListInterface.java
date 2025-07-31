package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Models.Empresa;

public interface DiaBloqueadoListInterface {
    DiaBloqueado list(long id, Usuario usuario);
    Page<DiaBloqueado> list(Empresa empresa, Pageable pageable);
}
