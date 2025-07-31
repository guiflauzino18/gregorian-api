package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.PlanoPaciente;

public interface PlanoPacienteListInterface {
    public PlanoPaciente list(long id, Usuario usuario);
    public Page<PlanoPaciente> list(Empresa empresa, Pageable pageable);
}
