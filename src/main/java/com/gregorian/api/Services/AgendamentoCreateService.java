package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.AgendamentoCreateDTO;
import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Services.Crud.AgendamentoService;
import com.gregorian.api.Interfaces.AgendamentoConverterInterface;
import com.gregorian.api.Interfaces.DateConverterInterface;

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
