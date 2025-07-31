package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.AgendamentoCreateDTO;
import com.gregorian.api.DTO.AgendamentoEditDTO;
import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Models.Usuario;

public interface AgendamentoConverterInterface {

    public Agendamento toAgendamento(AgendamentoCreateDTO dto, Usuario usuario);
    public Agendamento toAgendamento(AgendamentoEditDTO dto, Usuario usuario);
}
