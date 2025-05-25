package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Services.Interfaces.FeriadoValidateInterface;

@Service
public class FeriadoValidateService implements FeriadoValidateInterface{

    @Autowired
    private FeriadoService feriadoService;

    @Override
    public void jaExiste(String nome) {
        if (feriadoService.existByNome(nome)){
            throw new ConflictException("Já existe um feriado cadastrado com esse nome");
        }
    }
    
}
