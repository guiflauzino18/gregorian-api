package com.gregoryan.api.Services.Interfaces;

import java.util.Optional;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioValidateInterface {
    void jaExiste(String login);
    Usuario userExist(Optional<Usuario> usuario);
    
    // TODO Verifica se propriedade que está sendo manipulada pertence à mesma empresa do usuário que está logado. Se não, lança um Acesso negado.
    void isSameEmpresaFromUserLogged(Empresa empresaUser, Empresa empresa);
}
