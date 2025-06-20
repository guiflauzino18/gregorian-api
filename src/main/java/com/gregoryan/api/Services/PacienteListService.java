package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Interfaces.PacienteListInterface;

@Service
public class PacienteListService implements PacienteListInterface{

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    @Override
    public Paciente list(long id, Usuario usuario) {
        var paciente = pacienteService.findById(id).orElseThrow(() -> new EntityDontExistException("Paciente não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, paciente.getEmpresa());

        return paciente;
    }

    @Override
    public Page<Paciente> list(Empresa empresa, Pageable pageable) {
        var paciente = pacienteService.findByEmpresa(empresa, pageable);

        return paciente;
    }
    
}
