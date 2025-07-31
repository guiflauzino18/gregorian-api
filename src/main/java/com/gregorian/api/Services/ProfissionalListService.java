package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Services.Crud.ProfissionalService;
import com.gregorian.api.Interfaces.ProfissionalListInterface;

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
