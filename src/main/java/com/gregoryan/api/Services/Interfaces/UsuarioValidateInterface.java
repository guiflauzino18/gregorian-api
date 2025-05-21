package com.gregoryan.api.Services.Interfaces;

import java.util.Optional;

import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioValidateInterface {
    void jaExiste(UsuarioCadastroDTO dto);
    Usuario userExist(Optional<Usuario> usuario);
    void isSameEmpresaFromUserLogged(Empresa empresaUser, Empresa empresa);
}
