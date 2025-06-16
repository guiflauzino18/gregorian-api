package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.PlanoPaciente;

public interface PlanoPacienteListInterface {
    public PlanoPaciente list(long id, Empresa empresa);
    public Page<PlanoPaciente> list(Empresa empresa, Pageable pageable);
}
