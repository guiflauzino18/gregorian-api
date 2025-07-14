package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Interfaces.DiaBloqueadoConverterInterface;

@Service
public class DiaBloqueadoCreateService {
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;

    @Autowired
    private DiaBloqueadoConverterInterface diaBloqueadoConverter;

    public void create(DiaBloqueadoCadastroDTO dto, Usuario usuario){
        DiaBloqueado diaBloqueado = diaBloqueadoConverter.toDiaBloqueado(dto);
        diaBloqueado.setEmpresa(usuario.getEmpresa());
        diaBloqueadoService.save(diaBloqueado);
    }

}
