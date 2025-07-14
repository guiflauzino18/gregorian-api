package com.gregoryan.api.Components;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioValidateConflict implements UsuarioValidateInterface {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void validate(Usuario usuario, Empresa empresa) {
        if (usuarioService.existByLogin(usuario.getLogin())){
            throw new ConflictException("Já existe um usuário com este login");
        }
    }
}
