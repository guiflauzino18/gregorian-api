package com.gregoryan.api.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.UsuarioListInterface;

@Service
public class UsuarioListService implements UsuarioListInterface{

    @Autowired
    private UsuarioService service;
    @Autowired
    private UsuarioValidate usuarioValidate;

    @Override
    public Usuario list(long id, Empresa empresa) {

        Usuario usuario = service.findById(id).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, usuario.getEmpresa());

        return usuario;

    }

    @Override
    public Page<Usuario> list(Empresa empresa, Pageable pageable) {

        List<Usuario> usuarios = service.findByEmpresa(empresa, pageable).getContent();
        return new PageImpl<>(usuarios);
                
    }

    @Override
    public Usuario list(String login, Empresa empresa) {
        Usuario usuario = service.findByLogin(login).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, usuario.getEmpresa());
        return usuario;
    }

    @Override
    public Page<Usuario> list(Pageable pageable) {
        return service.findAll(pageable);

    }
    
}
