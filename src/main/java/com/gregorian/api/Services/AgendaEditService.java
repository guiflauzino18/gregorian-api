package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.AgendaEditDTO;
import com.gregorian.api.Services.Crud.AgendaService;
import com.gregorian.api.Interfaces.AgendaConverterInterface;

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
