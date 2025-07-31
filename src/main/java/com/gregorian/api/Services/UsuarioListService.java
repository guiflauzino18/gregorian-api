package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;
import com.gregorian.api.Interfaces.UsuarioListInterface;

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

    @Override
    public Page<Usuario> list(Pageable pageable, Usuario usuario, String input) {
        return service.search(usuario.getEmpresa().getId(), input, pageable);

    }

}
