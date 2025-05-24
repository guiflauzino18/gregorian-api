package com.gregoryan.api.Services.Interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;

public interface AgendaListInterface {

    Agenda list(long id, Empresa empresa);
    Page<Agenda> list(Empresa empresa, Pageable pageable);

}
