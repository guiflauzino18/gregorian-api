package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Interfaces.AgendaConverterInterface;

@Service
public class AgendaEditService {

    @Autowired
    private AgendaConverterInterface agendaConverter;
    @Autowired
    private AgendaService agendaService;
    
    public void edit(AgendaEditDTO dto, Usuario usuario){

        var agenda = agendaConverter.toAgenda(dto, usuario); // EntityDontExistException

        agendaService.save(agenda);

    }
}
