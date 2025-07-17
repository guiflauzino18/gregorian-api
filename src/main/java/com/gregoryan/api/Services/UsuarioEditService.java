package com.gregoryan.api.Services;

import com.gregoryan.api.Components.UsuarioValidateConflict;
import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;
import com.gregoryan.api.Models.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;

import java.util.List;

@Service
public class UsuarioEditService {
    
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioValidateConflict validateConflict;
    @Autowired
    private UsuarioValidateIsNotYourProperties isNotYourProperties;

    public Usuario edit(UsuarioEditDTO dto, Usuario usuario){

        Usuario usuarioConsulta = usuarioConverter.toUsuario(dto, usuario);
        isNotYourProperties.validate(usuario, usuarioConsulta.getEmpresa());
        return usuarioService.save(usuarioConsulta);

    }

}
