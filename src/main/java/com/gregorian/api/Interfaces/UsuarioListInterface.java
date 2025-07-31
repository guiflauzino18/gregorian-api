package com.gregorian.api.Interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Usuario;

public interface UsuarioListInterface {
    
    //findbyId
    Usuario list(long id, Usuario usuario);
    //findByEmpresa
    Page<Usuario> list(Empresa empresa, Pageable pageable);
    //findByLogin
    Usuario list(String login, Usuario usuario);
    //FindAll
    Page<Usuario> list(Pageable pageable);
    //Search
    Page<Usuario> list (Pageable pageable, Usuario usuario, String input);
}
