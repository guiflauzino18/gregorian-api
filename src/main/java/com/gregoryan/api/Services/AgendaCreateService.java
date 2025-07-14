package com.gregoryan.api.Services;

import com.gregoryan.api.Components.AgendaValidateConflict;
import com.gregoryan.api.Components.AgendaValidateDuplicateProfissional;
import com.gregoryan.api.Interfaces.AgendaValidateInterface;
import com.gregoryan.api.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Interfaces.StatusAgendaListInterface;

import java.util.List;

@Service
public class AgendaCreateService {

    @Autowired
    private AgendaService service;
    @Autowired
    private AgendaConverter converter;
    @Autowired
    private StatusAgendaListInterface statusAgendaList;
    @Autowired
    private ProfissionalListInterface profissionalList;
    @Autowired
    private AgendaValidateConflict validateConflict;
    @Autowired
    private AgendaValidateDuplicateProfissional validateDuplicateProfissional;
    
    public void create(AgendaCadastroDTO dto, Usuario usuario){
        Agenda agenda = converter.toAgenda(dto);
        Profissional profissional = profissionalList.list(dto.idProfissional(), usuario);
        agenda.setProfissional(profissional);
        agenda.setEmpresa(usuario.getEmpresa());
        StatusAgenda statusAgenda = statusAgendaList.list("Ativo", usuario);
        agenda.setStatusAgenda(statusAgenda);
        //validate(agenda);
        service.save(agenda);

    }

    private void validate(Agenda agenda){
        List<AgendaValidateInterface> validacoes = List.of(validateDuplicateProfissional, validateConflict);
        for (AgendaValidateInterface validacao : validacoes){
            validacao.validate(agenda);
        }
    }
}
