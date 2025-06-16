package com.gregoryan.api.Services.Interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Paciente;

public interface PacienteListInterface {

    public Paciente list(long id, Empresa empresa);
    public Page<Paciente> list(Empresa empresa, Pageable pageable);
}
