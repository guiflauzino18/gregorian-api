package com.gregorian.api.Components;

import com.gregorian.api.Models.StatusDia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregorian.api.Exception.ConflictException;
import com.gregorian.api.Services.Crud.StatusDiaService;
import com.gregorian.api.Interfaces.StatusDiaValidateInterface;

@Component
public class StatusDiaValidateConflict implements StatusDiaValidateInterface{

    @Autowired
    private StatusDiaService service;

    @Override
    public void validate(StatusDia statusDia) {
        if (service.existsByNome(statusDia.getNome())){
            throw new ConflictException("Já existe um status com esse nome");
        }
    }
    
}
