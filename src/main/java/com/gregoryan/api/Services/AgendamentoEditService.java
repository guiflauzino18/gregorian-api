package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Interfaces.AgendamentoConverterInterface;
import com.gregoryan.api.Interfaces.AgendamentoListInterface;

@Service
public class AgendamentoEditService {
    @Autowired
    private AgendamentoListInterface agendamentolist;
    @Autowired
    AgendamentoConverterInterface agendamentoConverter;
    @Autowired
    private AgendamentoService agendamentoService;


    public Agendamento edit(AgendamentoEditDTO dto, Usuario usuario){
        var agendamento = agendamentolist.list(dto.id(), usuario);
        var agendamentoNew = agendamentoConverter.toAgendamento(dto, usuario);

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