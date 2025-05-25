package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Interfaces.StatusDiaValidateInterface;

@Service
public class StatusDiaValidateService implements StatusDiaValidateInterface{

    @Autowired
    private StatusDiaService service;

    @Override
    public void jaExiste(String nome) {
        if (service.existsByNome(nome)){
            throw new ConflictException("Já existe um status com esse nome");
        }
    }
    
}
