package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Models.Empresa;

public interface AgendaListInterface {

    Agenda list(long id, Usuario usuario);
    Page<Agenda> list(Empresa empresa, Pageable pageable);

}
