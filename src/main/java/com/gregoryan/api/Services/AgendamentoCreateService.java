package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Services.Interfaces.AgendamentoConverterInterface;
import com.gregoryan.api.Services.Interfaces.DateConverterInterface;

@Service
public class AgendamentoCreateService {
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoConverterInterface agendamentoConverter;
    @Autowired
    private DateConverterInterface dateConverter;

    public Agendamento create(AgendamentoCreateDTO dto, Empresa empresa){

        var agendamento = agendamentoConverter.toAgendamento(dto, empresa);

        agendamento.setEmpresa(empresa);
        var dataRegistro = dateConverter.getDateCurrent();

        agendamento.setDataRegistro(dataRegistro);

        return agendamentoService.save(agendamento);
    }
}
