package com.gregoryan.api.Components;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Interfaces.AgendaValidateInterface;
import com.gregoryan.api.Services.Crud.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgendaValidateDuplicateProfissional implements AgendaValidateInterface {
    @Autowired
    private AgendaService agendaService;

    @Override
    public void validate(Agenda agenda) {
        if (agenda.getProfissional().getAgenda() != null){
            throw new ConflictException("Este profissional já possui agenda");
        }
    }
}
