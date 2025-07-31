package com.gregorian.api.Interfaces;



import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusAgenda;

public interface StatusAgendaListInterface {
    StatusAgenda list(long id, Usuario usuario);
    Page<StatusAgenda> list(Empresa empresa, Pageable pageable);
    StatusAgenda list(String nome, Usuario usuario);
}
