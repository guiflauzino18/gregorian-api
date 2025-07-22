package com.gregoryan.api.Services;

import com.gregoryan.api.Components.UsuarioValidateConflict;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.UsuarioCreateDTO;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.DateConverterInterface;
import java.util.List;

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

    
    public Usuario create(UsuarioCreateDTO dto, Usuario usuario){

        Usuario usuarioCreate = usuarioConverter.toUsuario(dto);
        usuarioCreate.setDataRegistro(dataConverter.getDateCurrent());
        usuarioCreate.setEmpresa(usuario.getEmpresa());
        usuarioCreate.setStatus(Usuario.StatusUsuario.ATIVO);
        usuarioValidateConflict.validate(usuarioCreate, usuarioCreate.getEmpresa());

        return usuarioService.save(usuarioCreate);
    }
}