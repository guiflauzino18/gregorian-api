package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Interfaces.DiaBloqueadoConverterInterface;

@Service
public class DiaBloqueadoEditingService {
    
    @Autowired
    private DiaBloqueadoConverterInterface diaBloqueadoConverter;
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;

    public void edit(DiaBloqueadoEditDTO dto, Empresa empresa){
        DiaBloqueado diaBloqueado = diaBloqueadoConverter.toDiaBloqueado(dto, empresa);
        diaBloqueadoService.save(diaBloqueado);

    }
}
