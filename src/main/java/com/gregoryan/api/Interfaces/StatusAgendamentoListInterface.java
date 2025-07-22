package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgendamento;
import com.gregoryan.api.Models.Usuario;

public interface StatusAgendamentoListInterface {

    public StatusAgendamento list(long id, Usuario usuario);
}
