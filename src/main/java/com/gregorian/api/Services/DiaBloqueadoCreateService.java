package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Services.Crud.DiaBloqueadoService;
import com.gregorian.api.Interfaces.DiaBloqueadoConverterInterface;

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
