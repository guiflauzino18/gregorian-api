package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.DiaBloqueadoEditDTO;
import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Services.Crud.DiaBloqueadoService;
import com.gregorian.api.Interfaces.DiaBloqueadoConverterInterface;

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
