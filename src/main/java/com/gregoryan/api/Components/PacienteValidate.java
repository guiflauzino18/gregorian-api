package com.gregoryan.api.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Interfaces.PacienteValidateInterface;

@Component
public class PacienteValidate implements PacienteValidateInterface{

    @Autowired
    private PacienteService pacienteService;

    @Override
    public void validate(Paciente paciente) {
        if (pacienteService.existsByCpf(paciente.getCpf()))
            throw new ConflictException("Já existe um paciente cadastrado com este CPF");

    }
    
}
