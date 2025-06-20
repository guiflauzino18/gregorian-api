package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioValidateInterface {

    // TODO Verifica se propriedade que está sendo manipulada pertence à mesma empresa do usuário que está logado. Se não, lança um Acesso negado.
    void validate(Usuario usuario, Empresa empresa);

}
