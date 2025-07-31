package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Usuario;

public interface UsuarioValidateInterface {

    // TODO Verifica se propriedade que está sendo manipulada pertence à mesma empresa do usuário que está logado. Se não, lança um Acesso negado.
    void validate(Usuario usuario, Empresa empresa);

}
