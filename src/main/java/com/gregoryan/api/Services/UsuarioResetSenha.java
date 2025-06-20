package com.gregoryan.api.Services;

import com.gregoryan.api.Components.CriptografarSenha;
import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;
import com.gregoryan.api.Models.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioResetSenhaDTO;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;

import java.util.List;

@Service
public class UsuarioResetSenha {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioValidateIsNotYourProperties validateIsNotYourProperties;
    @Autowired
    private CriptografarSenha criptografar;
    
    public void reset(UsuarioResetSenhaDTO dto, Usuario usuario){
        Usuario usuarioConsulta = usuarioService.findById(dto.id()).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        String senha = criptografar.criptografar(dto.senha());
        usuarioConsulta.setAlteraNextLogon(dto.alteraNextLogon());
        usuarioConsulta.setSenha(senha);
        validateIsNotYourProperties.validate(usuario, usuarioConsulta.getEmpresa());

        usuarioService.save(usuarioConsulta);
    }
}
