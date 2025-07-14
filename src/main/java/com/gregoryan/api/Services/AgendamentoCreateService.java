package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Interfaces.AgendamentoConverterInterface;
import com.gregoryan.api.Interfaces.DateConverterInterface;

@Service
public class AgendamentoCreateService {
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoConverterInterface agendamentoConverter;
    @Autowired
    private DateConverterInterface dateConverter;

    public Agendamento create(AgendamentoCreateDTO dto, Usuario usuario){

        var agendamento = agendamentoConverter.toAgendamento(dto, usuario);

        agendamento.setEmpresa(usuario.getEmpresa());
        var dataRegistro = dateConverter.getDateCurrent();

        agendamento.setDataRegistro(dataRegistro);

        return agendamentoService.save(agendamento);
    }
}
