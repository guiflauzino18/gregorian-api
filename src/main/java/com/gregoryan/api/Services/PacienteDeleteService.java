package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Interfaces.PacienteListInterface;

@Service
public class PacienteDeleteService {
    @Autowired
    private PacienteListInterface pacienteList;
    @Autowired
    private PacienteService pacienteService;
    
    public void delete(long id, Empresa empresa){

        var paciente = pacienteList.list(id, empresa);
        pacienteService.delete(paciente);
    }
}
