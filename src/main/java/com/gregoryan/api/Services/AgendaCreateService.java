package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaListInterface;

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
    
    public void cadastrar(AgendaCadastroDTO dto, Empresa empresa){
        Agenda agenda = converter.toAgenda(dto);

        Profissional profissional = profissionalList.list(dto.idProfissional(), empresa);
        agenda.setProfissional(profissional);
        agenda.setEmpresa(empresa);

        StatusAgenda statusAgenda = statusAgendaList.list("Ativo", empresa);
        agenda.setStatusAgenda(statusAgenda);

        service.save(agenda);

    }
}
