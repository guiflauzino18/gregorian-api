package com.gregorian.api.Interfaces;


import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Paciente;

public interface PacienteListInterface {

    public Paciente list(long id, Usuario usuario);
    public Page<Paciente> list(Empresa empresa, Pageable pageable);
}
