package com.gregoryan.api.Interfaces;


import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Paciente;

public interface PacienteListInterface {

    public Paciente list(long id, Usuario usuario);
    public Page<Paciente> list(Empresa empresa, Pageable pageable);
}
