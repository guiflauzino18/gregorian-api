package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateConflict;
import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Exception.EntityDontExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.UsuarioEditDTO;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;

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

    private Logger logger = LoggerFactory.getLogger(UsuarioEditService.class);

    public Usuario edit(UsuarioEditDTO dto, Usuario usuario){

        Usuario oldData = usuarioService.findById(dto.getId()).orElseThrow(() -> new EntityDontExistException("Usuário não enontrado"));
        Usuario usuarioConsulta = usuarioConverter.toUsuario(dto, usuario);
        isNotYourProperties.validate(usuario, usuarioConsulta.getEmpresa());
        var result = usuarioService.save(usuarioConsulta);
        logger.warn("update: %s editou %s. Dados antigos: %s Dados Novos: %s"
                .formatted(usuario.getLogin(), usuarioConsulta.getLogin(), oldData.toString(), usuarioConsulta.toString()));

        return result;

    }

}
