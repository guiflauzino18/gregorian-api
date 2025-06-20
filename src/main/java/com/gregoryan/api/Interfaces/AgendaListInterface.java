package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;

public interface AgendaListInterface {

    Agenda list(long id, Usuario usuario);
    Page<Agenda> list(Empresa empresa, Pageable pageable);

}
