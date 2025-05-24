package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Interfaces.StatusAgendaValidateInterface;

@Service
public class StatusAgendaValidateService implements StatusAgendaValidateInterface{

    @Autowired
    private StatusAgendaService statusAgendaService;

    @Override
    public void jaExiste(String nome) {
        if (statusAgendaService.existsByNome(nome)){
            throw new ConflictException("Já existe um status cadastrado com este nome");
        }
    }
    
}
