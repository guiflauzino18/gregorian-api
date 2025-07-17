package com.gregoryan.api.Interfaces;



import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;

public interface StatusAgendaListInterface {
    StatusAgenda list(long id, Usuario usuario);
    Page<StatusAgenda> list(Empresa empresa, Pageable pageable);
    StatusAgenda list(String nome, Usuario usuario);
}
