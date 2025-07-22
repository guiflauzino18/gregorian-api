package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface AgendamentoListInterface {
    public Agendamento list(long id, Usuario usuario);
}
