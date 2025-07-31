package com.gregorian.api.Services;

import com.gregorian.api.Components.AgendaValidateConflict;
import com.gregorian.api.Components.AgendaValidateDuplicateProfissional;
import com.gregorian.api.Interfaces.AgendaValidateInterface;
import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.StatusAgenda;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.AgendaCadastroDTO;
import com.gregorian.api.Services.Crud.AgendaService;
import com.gregorian.api.Interfaces.ProfissionalListInterface;
import com.gregorian.api.Interfaces.StatusAgendaListInterface;

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
