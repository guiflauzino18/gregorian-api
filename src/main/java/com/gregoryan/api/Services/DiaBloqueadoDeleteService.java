package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Interfaces.DiaBloqueadoListInterface;

@Service
public class DiaBloqueadoDeleteService {
    @Autowired
    private DiaBloqueadoListInterface diaBloqueadoList;
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;
    
    public void delete(long id, Usuario usuario){
        DiaBloqueado diaBloqueado = diaBloqueadoList.list(id, usuario);
        diaBloqueadoService.delete(diaBloqueado);
    }
}
