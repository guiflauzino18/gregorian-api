package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Interfaces.StatusDiaListInterface;

@Service
public class StatusDiaListService implements StatusDiaListInterface{

    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    

    @Override
    public Page<StatusDia> list(Empresa empresa, Pageable pageable) {
        return statusDiaService.findByEmpresa(empresa, pageable);
    }

    @Override
    public StatusDia list(long id, Usuario usuario) {
        StatusDia statusDia = statusDiaService.findById(id).orElseThrow(() -> new EntityDontExistException("Status não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, statusDia.getEmpresa());
        return statusDia;
    }

    @Override
    public StatusDia list(String nome, Usuario usuario) {
        StatusDia statusDia = statusDiaService.findByNome(nome).orElseThrow(() -> new EntityDontExistException("Status Dia não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, statusDia.getEmpresa());

        return statusDia;
    }
    
}
