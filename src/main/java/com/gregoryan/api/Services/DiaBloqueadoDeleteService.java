package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Interfaces.DiaBloqueadoListInterface;

@Service
public class DiaBloqueadoDeleteService {
    @Autowired
    private DiaBloqueadoListInterface diaBloqueadoList;
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;
    
    public void delete(long id, Empresa empresa){
        DiaBloqueado diaBloqueado = diaBloqueadoList.list(id, empresa);
        diaBloqueadoService.delete(diaBloqueado);
    }
}
