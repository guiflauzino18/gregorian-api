package com.gregoryan.api.Components;

import com.gregoryan.api.Models.StatusHora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Interfaces.StatusHoraValidateInterface;

@Component
public class StatusHoraValidateConflict implements StatusHoraValidateInterface{

    @Autowired
    private StatusHoraService statusHoraService;

    @Override
    public void validate(StatusHora statusHora) {
        if (statusHoraService.existsByNome(statusHora.getNome())){
            throw new ConflictException("Já existe um status com esse nome");
        }
    }
    
}
