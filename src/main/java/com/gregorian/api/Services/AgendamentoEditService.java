package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.AgendamentoEditDTO;
import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Services.Crud.AgendamentoService;
import com.gregorian.api.Interfaces.AgendamentoConverterInterface;
import com.gregorian.api.Interfaces.AgendamentoListInterface;

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