package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusHora;

public interface StatusHoraListInterface {
    
    StatusHora list(long id, Usuario usuario); // lista por ID
    Page<StatusHora> list(Empresa empresa, Pageable pageable); // lista por Empresa
    StatusHora list(String nome, Usuario usuario);


}
