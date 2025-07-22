package com.gregoryan.api.Services;

import com.gregoryan.api.Exception.AlreadyBlockedException;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Interfaces.DiaBloqueadoValidateInterface;

@Service
public class DiaBloqueadoValidateAlreadyBlockedService implements DiaBloqueadoValidateInterface{

    @Autowired
    private DiaBloqueadoService diaBloqueadoService;

    @Override
    public void validate(DiaBloqueado diaBloqueado) {
        //Se dia já existir no banco então já está bloqueado
        var dia = diaBloqueadoService.findByDia(diaBloqueado.getDia());
        if (dia.isPresent()){
            throw new AlreadyBlockedException("Dia já bloqueado");
        }
    }
}
