package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;

@Service
public class UsuarioEditingService {
    
    @Autowired
    private UsuarioValidate validate;
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private UsuarioService usuarioService;

    public Usuario edit(UsuarioEditDTO dto, Empresa empresa){

        Usuario usuario = usuarioService.findById(dto.id()).orElseThrow(()-> new EntityDontExistException("Usuário não encontrado"));
        
        validate.isSameEmpresaFromUserLogged(empresa, usuario.getEmpresa());

        usuario = usuarioConverter.toUsuario(dto, empresa);

        return usuarioService.save(usuario);

    }

}
