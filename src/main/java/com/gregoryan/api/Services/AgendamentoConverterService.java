package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Interfaces.AgendamentoConverterInterface;
import com.gregoryan.api.Interfaces.DateConverterInterface;
import com.gregoryan.api.Interfaces.PacienteListInterface;
import com.gregoryan.api.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Interfaces.StatusAgendamentoListInterface;

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