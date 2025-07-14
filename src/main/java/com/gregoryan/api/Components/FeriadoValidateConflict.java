package com.gregoryan.api.Components;

import com.gregoryan.api.Models.Feriado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Interfaces.FeriadoValidateInterface;

@Component
public class FeriadoValidateConflict implements FeriadoValidateInterface{

    @Autowired
    private FeriadoService feriadoService;

    @Override
    public void validate(Feriado feriado) {
        if (feriadoService.existByNome(feriado.getNome())){
            throw new ConflictException("Já existe um feriado cadastrado com esse nome");
        }
    }
}
