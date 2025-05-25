package com.gregoryan.api.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class UsuarioValidate implements UsuarioValidateInterface{

    @Autowired
    private UsuarioService usuarioservice;

    @Override
    public void jaExiste(String login) {
        if (usuarioservice.existByLogin(login)){

            throw new ConflictException("Já existe um usuário com este login");
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
        
        throw new EntityDontExistException("Usuáio não encontrado.");
        
    }
    
}
