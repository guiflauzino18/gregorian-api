package com.gregoryan.api.Components;

import com.gregoryan.api.Models.StatusAgenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Interfaces.StatusAgendaValidateInterface;

@Component
public class StatusAgendaValidateConflict implements StatusAgendaValidateInterface{

    @Autowired
    private StatusAgendaService statusAgendaService;

    @Override
    public void validate(StatusAgenda statusAgenda) {
        if (statusAgendaService.existsByNome(statusAgenda.getNome())){
            throw new ConflictException("Já existe um status cadastrado com este nome");
        }
    }
    
}
