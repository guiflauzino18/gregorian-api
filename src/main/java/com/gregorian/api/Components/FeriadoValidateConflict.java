package com.gregorian.api.Components;

import com.gregorian.api.Models.Feriado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregorian.api.Exception.ConflictException;
import com.gregorian.api.Services.Crud.FeriadoService;
import com.gregorian.api.Interfaces.FeriadoValidateInterface;

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
