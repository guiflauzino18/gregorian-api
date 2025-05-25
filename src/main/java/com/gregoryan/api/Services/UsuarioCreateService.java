package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.DataConverterInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class UsuarioCreateService{

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioConverter usuarioConverter;
    @Autowired
    private UsuarioValidateInterface validateUsuario;
    @Autowired
    private DataConverterInterface dataConverter;

    
    public Usuario cadastrar(UsuarioCadastroDTO dto, Empresa empresa){
        validateUsuario.jaExiste(dto.login());

        Usuario usuario = usuarioConverter.toUsuario(dto);
        usuario.setDataRegistro(dataConverter.getDateCurrent());
        usuario.setEmpresa(empresa);
        usuario.setStatus(Usuario.StatusUsuario.ATIVO);

        return usuarioService.save(usuario);
    }

    
}