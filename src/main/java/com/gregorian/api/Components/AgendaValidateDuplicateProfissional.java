package com.gregorian.api.Components;

import com.gregorian.api.Exception.ConflictException;
import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Interfaces.AgendaValidateInterface;
import com.gregorian.api.Services.Crud.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgendaValidateDuplicateProfissional implements AgendaValidateInterface {
    @Autowired
    private AgendaService agendaService;

    @Override
    public void validate(Agenda agenda) {
        if(agendaService.existsByProfissional(agenda.getProfissional())){
            throw new ConflictException("Já existe uma agenda para este profissional");
        }
    }
}
