package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Interfaces.PacienteValidateInterface;

@Service
public class PacienteValidateService implements PacienteValidateInterface{

    @Autowired
    private PacienteService pacienteService;

    @Override
    public void validate(Paciente paciente) {
        if (pacienteService.existsByCpf(paciente.getCpf()))
            throw new ConflictException("Já existe um paciente cadastrado com este CPF");

    }
    
}
