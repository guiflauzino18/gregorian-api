package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Services.Crud.DiaBloqueadoService;
import com.gregorian.api.Interfaces.DiaBloqueadoListInterface;

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
