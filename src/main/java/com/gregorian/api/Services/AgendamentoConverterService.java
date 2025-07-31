package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.AgendamentoCreateDTO;
import com.gregorian.api.DTO.AgendamentoEditDTO;
import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Interfaces.AgendamentoConverterInterface;
import com.gregorian.api.Interfaces.DateConverterInterface;
import com.gregorian.api.Interfaces.PacienteListInterface;
import com.gregorian.api.Interfaces.ProfissionalListInterface;
import com.gregorian.api.Interfaces.StatusAgendamentoListInterface;

@Service
public class AgendamentoConverterService implements AgendamentoConverterInterface{

    @Autowired
    private ProfissionalListInterface profissionalList;
    @Autowired
    private PacienteListInterface pacienteList;
    @Autowired
    private StatusAgendamentoListInterface statusAgendamentoList;
    @Autowired
    private DateConverterInterface dateConverter;

    @Override
    public Agendamento toAgendamento(AgendamentoCreateDTO dto, Usuario usuario) {
        var agendamento = new Agendamento();
        var profissional = profissionalList.list(dto.profissional(), usuario);
        var paciente = pacienteList.list(dto.paciente(), usuario);
        var status = statusAgendamentoList.list(dto.statusAgendamento(), usuario);
        var date = dateConverter.toCalendar(dto.data(), dto.hora());

        agendamento.setProfissional(profissional);
        agendamento.setPaciente(paciente);
        agendamento.setStatusAgendamento(status);
        agendamento.setData(date);

        return agendamento;

    }

    @Override
    public Agendamento toAgendamento(AgendamentoEditDTO dto, Usuario usuario) {
        var profissional = profissionalList.list(dto.idProfissional(), usuario);
        var paciente = pacienteList.list(dto.idPaciente(), usuario);
        var status = statusAgendamentoList.list(dto.idStatus(), usuario);
        var date = dateConverter.toCalendar(dto.data(), dto.hora());

        var agendamento = new Agendamento();
        agendamento.setProfissional(profissional);
        agendamento.setPaciente(paciente);
        agendamento.setStatusAgendamento(status);
        agendamento.setData(date);
        return agendamento;
    }
    
}