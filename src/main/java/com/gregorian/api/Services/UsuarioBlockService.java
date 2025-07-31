package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Interfaces.UsuarioListInterface;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;
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

        var currentStatus = usuario.getStatus();
        usuario.setStatus(
                currentStatus != Usuario.StatusUsuario.BLOQUEADO ? Usuario.StatusUsuario.BLOQUEADO
                        : Usuario.StatusUsuario.ATIVO
        );

        service.save(usuario);
    }
}
