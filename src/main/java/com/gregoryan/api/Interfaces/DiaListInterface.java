package com.gregoryan.api.Interfaces;


import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;

public interface DiaListInterface {
    
    //findById
    Dias list(long id, Usuario usuario);

    //findByEmpresa
    Page<Dias> list(Empresa empresa, Pageable pageable);
}
