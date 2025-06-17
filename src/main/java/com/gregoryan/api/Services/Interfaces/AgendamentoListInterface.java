package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;

public interface AgendamentoListInterface {
    public Agendamento list(long id, Empresa empresa);
}
