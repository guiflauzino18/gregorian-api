package com.gregoryan.api.Components;

import com.gregoryan.api.Models.StatusDia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Interfaces.StatusDiaValidateInterface;

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
