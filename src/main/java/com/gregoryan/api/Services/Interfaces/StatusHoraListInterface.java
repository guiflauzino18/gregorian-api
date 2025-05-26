package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusHora;

public interface StatusHoraListInterface {
    
    StatusHora list(long id, Empresa empresa); // lista por ID
    Page<StatusHora> list(Empresa empresa, Pageable pageable); // lista por Empresa
    StatusHora list(String nome, Empresa empresa);


}
