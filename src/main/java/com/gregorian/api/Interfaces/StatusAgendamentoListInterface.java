package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.StatusAgendamento;
import com.gregorian.api.Models.Usuario;

public interface StatusAgendamentoListInterface {

    public StatusAgendamento list(long id, Usuario usuario);
}
