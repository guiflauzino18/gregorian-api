package com.gregoryan.api.Services;

import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregoryan.api.Interfaces.UsuarioListInterface;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioBlockService {
    @Autowired
    private UsuarioListInterface list;
    @Autowired
    private UsuarioService service;
    @Autowired
    private UsuarioValidateIsNotYourProperties validate;

    public void block(long id, Usuario usuarioLogado){

        Usuario usuario = list.list(id, usuarioLogado );
        validate.validate(usuarioLogado, usuario.getEmpresa());

        usuario.setStatus(Usuario.StatusUsuario.BLOQUEADO);;

        service.save(usuario);
    }
}
