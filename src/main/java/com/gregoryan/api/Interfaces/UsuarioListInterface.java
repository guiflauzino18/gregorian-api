package com.gregoryan.api.Interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioListInterface {
    
    //findbyId
    Usuario list(long id, Usuario usuario);
    //findByEmpresa
    Page<Usuario> list(Empresa empresa, Pageable pageable);
    //findByLogin
    Usuario list(String login, Usuario usuario);
    //FindAll
    Page<Usuario> list(Pageable pageable);
}
