package com.gregoryan.api.Services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Interfaces.AgendamentoConverterInterface;
import com.gregoryan.api.Services.Interfaces.DateConverterInterface;
import com.gregoryan.api.Services.Interfaces.PacienteListInterface;
import com.gregoryan.api.Services.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendamentoListInterface;

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
    public Agendamento toAgendamento(AgendamentoCreateDTO dto, Empresa empresa) {
        var agendamento = new Agendamento();
        var profissional = profissionalList.list(dto.profissional(), empresa);
        var paciente = pacienteList.list(dto.paciente(), empresa);
        var status = statusAgendamentoList.list(dto.statusAgendamento(), empresa);
        var date = dateConverter.toCalendar(dto.data(), dto.hora());

        agendamento.setProfissional(profissional);
        agendamento.setPaciente(paciente);
        agendamento.setStatusAgendamento(status);
        agendamento.setData(date);

        return agendamento;

    }

    @Override
    public Agendamento toAgendamento(AgendamentoEditDTO dto, Empresa empresa) {
        var profissional = profissionalList.list(dto.idProfissional(), empresa);
        var paciente = pacienteList.list(dto.idPaciente(), empresa);
        var status = statusAgendamentoList.list(dto.idStatus(), empresa);
        var date = dateConverter.toCalendar(dto.data(), dto.hora());

        var agendamento = new Agendamento();
        agendamento.setProfissional(profissional);
        agendamento.setPaciente(paciente);
        agendamento.setStatusAgendamento(status);
        agendamento.setData(date);
        return agendamento;
    }
    
}

/**
 *     long id,
    long idProfissional,
    long idPaciente,
    long idStatus,
    String data,
    String hora
 */