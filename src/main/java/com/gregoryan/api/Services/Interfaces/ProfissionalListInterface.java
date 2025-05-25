package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;

public interface ProfissionalListInterface {
    
    Profissional list(long id, Empresa empresa);
    Page<Profissional> list(Empresa empresa, Pageable pageable);
}
