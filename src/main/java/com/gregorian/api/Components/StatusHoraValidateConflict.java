package com.gregorian.api.Components;

import com.gregorian.api.Models.StatusHora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregorian.api.Exception.ConflictException;
import com.gregorian.api.Services.Crud.StatusHoraService;
import com.gregorian.api.Interfaces.StatusHoraValidateInterface;

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
