package com.gregoryan.api.Components;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;

@Component
public class UsuarioValidateIsNotYourProperties{
    @Autowired
    private UsuarioService usuarioservice;

    // TODO Verifica se propriedade que está sendo manipulada pertence à mesma empresa do usuário que está logado. Se não, lança um Acesso negado.
    public void validate(Usuario usuario, Empresa empresa) {

        if (usuario.getEmpresa().getId() != empresa.getId()){
            throw new AcessoNegadoException("Esta propriedade não pertence a sua Empresa");
        }
    }
}
