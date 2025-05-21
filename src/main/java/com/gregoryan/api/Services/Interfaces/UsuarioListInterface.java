package com.gregoryan.api.Services.Interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioListInterface {
    
    //findbyId
    UsuarioResponseDTO list(long id);
    //findByEmpresa
    Page<UsuarioResponseDTO> list(Empresa empresa, Pageable pageable);
    //findByLogin
    UsuarioResponseDTO list(String login);
    //FindAll
    Page<UsuarioResponseDTO> list(Pageable pageable);
}
