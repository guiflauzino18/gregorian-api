package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Interfaces.DiaBloqueadoConverterInterface;

@Service
public class DiaBloqueadoEditingService {
    
    @Autowired
    private DiaBloqueadoConverterInterface diaBloqueadoConverter;
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;

    public void edit(DiaBloqueadoEditDTO dto, Usuario usuario){
        DiaBloqueado diaBloqueado = diaBloqueadoConverter.toDiaBloqueado(dto, usuario);
        diaBloqueadoService.save(diaBloqueado);

    }
}
