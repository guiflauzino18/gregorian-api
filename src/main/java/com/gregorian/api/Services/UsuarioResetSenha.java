package com.gregorian.api.Services;

import com.gregorian.api.Components.CriptografarSenha;
import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.UsuarioResetSenhaDTO;
import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;

@Service
public class UsuarioResetSenha {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioValidateIsNotYourProperties validateIsNotYourProperties;
    @Autowired
    private CriptografarSenha criptografar;

    private Logger logger = LoggerFactory.getLogger(UsuarioResetSenha.class);
    
    public void reset(UsuarioResetSenhaDTO dto, Usuario usuario){
        Usuario usuarioConsulta = usuarioService.findById(dto.id()).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        String senha = criptografar.criptografar(dto.senha());
        usuarioConsulta.setAlteraNextLogon(dto.alteraNextLogon());
        usuarioConsulta.setSenha(senha);
        validateIsNotYourProperties.validate(usuario, usuarioConsulta.getEmpresa());
        usuarioService.save(usuarioConsulta);
        logger.info("update: %s resetou a senha de %s".formatted(usuario.getLogin(), usuarioConsulta.getLogin()));
    }
}
