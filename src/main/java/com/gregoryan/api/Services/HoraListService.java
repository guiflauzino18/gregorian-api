package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Horas;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Services.Interfaces.HoraListInterface;

@Service
public class HoraListService implements HoraListInterface{

    @Autowired
    private HorasService horaService;

    @Override
    public Horas list(long id) {
        var hora = horaService.findById(id).orElseThrow(() -> new EntityDontExistException("Hora não encontrada"));

        return hora;
    }
    
}
