package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;

public interface AgendamentoConverterInterface {
    
    public Agendamento toAgendamento(AgendamentoCreateDTO dto, Empresa empresa);
    public Agendamento toAgendamento(AgendamentoEditDTO dto, Empresa empresa);
}
