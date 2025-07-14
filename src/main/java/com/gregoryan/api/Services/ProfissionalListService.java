package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Interfaces.ProfissionalListInterface;

@Service
public class ProfissionalListService implements ProfissionalListInterface{

    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    @Override
    public Profissional list(long id, Usuario usuario) {
        Profissional profissional = profissionalService.findById(id).orElseThrow(() -> new EntityDontExistException("Profissional não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, profissional.getUsuario().getEmpresa());

        return profissional;
    }

    @Override
    public Page<Profissional> list(Empresa empresa, Pageable pageable) {
        
        return profissionalService.findByEmpresa(empresa.getId(), pageable);
    }
    
}
