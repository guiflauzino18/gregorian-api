package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateConflict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.UsuarioCreateDTO;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;
import com.gregorian.api.Interfaces.DateConverterInterface;

@Service
public class UsuarioCreateService{

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private UsuarioValidateConflict usuarioValidateConflict;
    @Autowired
    private DateConverterInterface dataConverter;

    private Logger logger = LoggerFactory.getLogger(UsuarioCreateService.class);

    
    public Usuario create(UsuarioCreateDTO dto, Usuario usuario){

        Usuario usuarioCreate = usuarioConverter.toUsuario(dto);
        usuarioCreate.setDataRegistro(dataConverter.getDateCurrent());
        usuarioCreate.setEmpresa(usuario.getEmpresa());
        usuarioCreate.setStatus(Usuario.StatusUsuario.ATIVO);
        usuarioValidateConflict.validate(usuarioCreate, usuarioCreate.getEmpresa());

        var result = usuarioService.save(usuarioCreate);
        logger.info("crete: %s cadastrou o usuário %s".formatted(usuario.getLogin(), usuarioCreate.getLogin()));
        return result;
    }
}