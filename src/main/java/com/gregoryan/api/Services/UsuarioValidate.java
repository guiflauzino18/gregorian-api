package com.gregoryan.api.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.UsuarioDontExistException;
import com.gregoryan.api.Exception.UsuarioExisteException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class UsuarioValidate implements UsuarioValidateInterface{

    @Autowired
    private UsuarioService usuarioservice;

    @Override
    public void jaExiste(UsuarioCadastroDTO dto) {
        if (usuarioservice.existByLogin(dto.login())){

            throw new UsuarioExisteException("Login já existe");
        }
    }

    @Override
    public void isSameEmpresaFromUserLogged(Empresa empresaUser, Empresa empresa) {
        
        if (empresaUser != empresa){
            throw new AcessoNegadoException("Esta propriedade não pertence a sua Empresa");
        }
        //return empresaUser == empresa;
    }

    @Override
    public Usuario userExist(Optional<Usuario> usuario) {
        
        if (usuario.isPresent())
            return usuario.get();
        
        throw new UsuarioDontExistException("Usuáio não encontrado.");
        
    }
    
}
