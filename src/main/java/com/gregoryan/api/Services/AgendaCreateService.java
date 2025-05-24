package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendaService;

@Service
public class AgendaCreateService {

    @Autowired
    private AgendaService service;
    @Autowired
    private AgendaConverter converter;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private StatusAgendaService statusService;
    
    public void cadastrar(AgendaCadastroDTO dto, Empresa empresa){
        Agenda agenda = converter.toAgenda(dto);

        Profissional profissional = profissionalService.findById(dto.idProfissional()).orElseThrow(() -> new EntityDontExistException("Profissionao não encontrado"));
        agenda.setProfissional(profissional);
        agenda.setEmpresa(empresa);

        StatusAgenda status = statusService.findByNome("Ativo").orElseThrow(() -> new EntityDontExistException("Status Ativo da Agenda não existe"));
        
        agenda.setStatusAgenda(status);

        service.save(agenda);

    }
}
