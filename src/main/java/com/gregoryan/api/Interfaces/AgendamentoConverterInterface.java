package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface AgendamentoConverterInterface {

    public Agendamento toAgendamento(AgendamentoCreateDTO dto, Usuario usuario);
    public Agendamento toAgendamento(AgendamentoEditDTO dto, Usuario usuario);
}
