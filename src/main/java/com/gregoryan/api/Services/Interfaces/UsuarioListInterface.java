package com.gregoryan.api.Services.Interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioListInterface {
    
    //findbyId
    Usuario list(long id, Empresa empresa);
    //findByEmpresa
    Page<Usuario> list(Empresa empresa, Pageable pageable);
    //findByLogin
    Usuario list(String login, Empresa empresa);
    //FindAll
    Page<Usuario> list(Pageable pageable);
}
