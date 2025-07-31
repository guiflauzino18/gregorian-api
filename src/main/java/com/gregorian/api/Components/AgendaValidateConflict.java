package com.gregorian.api.Components;

import com.gregorian.api.Exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Services.Crud.AgendaService;
import com.gregorian.api.Interfaces.AgendaValidateInterface;

@Component
public class AgendaValidateConflict implements AgendaValidateInterface{

    @Autowired
    private AgendaService agendaService;

    @Override
    public void validate(Agenda agenda) {
        if (agendaService.existsByNome(agenda.getNome()))
            throw new ConflictException("Já existe uma agenda com este nome");
    }
}
