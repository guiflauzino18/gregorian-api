package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Services.Interfaces.AgendamentoConverterInterface;
import com.gregoryan.api.Services.Interfaces.AgendamentoListInterface;

@Service
public class AgendamentoEditService {
    @Autowired
    private AgendamentoListInterface agendamentolist;
    @Autowired
    AgendamentoConverterInterface agendamentoConverter;
    @Autowired
    private AgendamentoService agendamentoService;


    public Agendamento edit(AgendamentoEditDTO dto, Empresa empresa){
        var agendamento = agendamentolist.list(dto.id(), empresa);
        var agendamentoNew = agendamentoConverter.toAgendamento(dto, empresa);

        agendamento.setProfissional(agendamentoNew.getProfissional());
        agendamento.setPaciente(agendamentoNew.getPaciente());
        agendamento.setStatusAgendamento(agendamentoNew.getStatusAgendamento());
        agendamento.setData(agendamentoNew.getData());

        return agendamentoService.save(agendamento);

    }
}

/**
 *         agendamento.setProfissional(profissional);
        agendamento.setPaciente(paciente);
        agendamento.setStatusAgendamento(status);
        agendamento.setData(date);
 */