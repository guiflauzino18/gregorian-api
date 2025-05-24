package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioResetSenhaDTO;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;

@Service
public class UsuarioResetSenha {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioValidate validate;
    @Autowired
    private CriptografarSenha criptografar;
    
    public void reset(UsuarioResetSenhaDTO dto, Empresa empresa){
        Usuario usuario = usuarioService.findById(dto.id()).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        validate.isSameEmpresaFromUserLogged(empresa, usuario.getEmpresa());

        String senha = criptografar.criptografar(dto.senha());
        usuario.setAlteraNextLogon(dto.alteraNextLogon());
        usuario.setSenha(senha);

        usuarioService.save(usuario);
    }
}
