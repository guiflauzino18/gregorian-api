package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Interfaces.PacienteListInterface;

@Service
public class PacienteDeleteService {
    @Autowired
    private PacienteListInterface pacienteList;
    @Autowired
    private PacienteService pacienteService;
    
    public void delete(long id, Usuario usuario){

        var paciente = pacienteList.list(id, usuario);
        pacienteService.delete(paciente);
    }
}
