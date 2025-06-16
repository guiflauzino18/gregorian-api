package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.AgendaConverterInterface;

@Service
public class AgendaEditService {

    @Autowired
    private AgendaConverterInterface agendaConverter;
    @Autowired
    private AgendaService agendaService;
    
    public void edit(AgendaEditDTO dto, Empresa empresa){

        var agenda = agendaConverter.toAgenda(dto, empresa); // EntityDontExistException

        agendaService.save(agenda);

    }
}
