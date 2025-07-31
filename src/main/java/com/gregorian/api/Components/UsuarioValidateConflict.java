package com.gregorian.api.Components;

import com.gregorian.api.Exception.ConflictException;
import com.gregorian.api.Interfaces.UsuarioValidateInterface;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;
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
