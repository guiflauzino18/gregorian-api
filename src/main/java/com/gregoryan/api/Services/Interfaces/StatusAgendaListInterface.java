package com.gregoryan.api.Services.Interfaces;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;

public interface StatusAgendaListInterface {
    StatusAgenda list(long id, Empresa empresa);
    Page<StatusAgenda> list(Empresa empresa, Pageable pageable);
    StatusAgenda list(String nome, Empresa empresa);

}
