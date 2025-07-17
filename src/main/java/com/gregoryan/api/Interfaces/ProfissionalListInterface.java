package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;

public interface ProfissionalListInterface {
    
    Profissional list(long id, Usuario usuario);
    Page<Profissional> list(Empresa empresa, Pageable pageable);
}
