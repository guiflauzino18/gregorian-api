package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Models.Usuario;

public interface AgendamentoListInterface {
    public Agendamento list(long id, Usuario usuario);
}
