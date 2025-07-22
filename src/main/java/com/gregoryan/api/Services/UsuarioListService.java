package com.gregoryan.api.Services;

import java.util.List;

import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.UsuarioListInterface;

@Service
public class UsuarioListService implements UsuarioListInterface{

    @Autowired
    private UsuarioService service;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    @Override
    public Usuario list(long id, Usuario usuario) {

        Usuario usuarioConsulta = service.findById(id).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, usuario.getEmpresa());

        return usuarioConsulta;

    }

    @Override
    public Page<Usuario> list(Empresa empresa, Pageable pageable) {

        return service.findByEmpresa(empresa, pageable);

    }

    @Override
    public Usuario list(String login, Usuario usuario) {
        Usuario usuarioConsulta = service.findByLogin(login).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, usuario.getEmpresa());
        return usuarioConsulta;
    }

    @Override
    public Page<Usuario> list(Pageable pageable) {
        return service.findAll(pageable);

    }
    
}
