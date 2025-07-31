package com.gregorian.api.Interfaces;


import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Dias;
import com.gregorian.api.Models.Empresa;

public interface DiaListInterface {
    
    //findById
    Dias list(long id, Usuario usuario);

    //findByEmpresa
    Page<Dias> list(Empresa empresa, Pageable pageable);
}
