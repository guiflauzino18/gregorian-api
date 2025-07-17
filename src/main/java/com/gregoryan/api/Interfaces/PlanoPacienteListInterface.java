package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.PlanoPaciente;

public interface PlanoPacienteListInterface {
    public PlanoPaciente list(long id, Usuario usuario);
    public Page<PlanoPaciente> list(Empresa empresa, Pageable pageable);
}
