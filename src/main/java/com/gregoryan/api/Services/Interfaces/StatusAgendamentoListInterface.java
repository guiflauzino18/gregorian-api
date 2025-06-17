package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgendamento;

public interface StatusAgendamentoListInterface {

    public StatusAgendamento list(long id, Empresa empresa);
}
