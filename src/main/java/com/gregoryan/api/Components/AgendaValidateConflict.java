package com.gregoryan.api.Components;

import com.gregoryan.api.Exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Interfaces.AgendaValidateInterface;

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
