package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Interfaces.DiaBloqueadoConverterInterface;

@Service
public class DiaBloqueadoCreateService {
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;

    @Autowired
    private DiaBloqueadoConverterInterface diaBloqueadoConverter;

    public void create(DiaBloqueadoCadastroDTO dto, Empresa empresa){
        DiaBloqueado diaBloqueado = diaBloqueadoConverter.toDiaBloqueado(dto);
        diaBloqueado.setEmpresa(empresa);
        diaBloqueadoService.save(diaBloqueado);
    }

}
