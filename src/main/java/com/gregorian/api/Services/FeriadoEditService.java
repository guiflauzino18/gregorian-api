package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.FeriadoEditDTO;
import com.gregorian.api.Models.Feriado;
import com.gregorian.api.Services.Crud.FeriadoService;
import com.gregorian.api.Interfaces.FeriadoConverterInterface;
import com.gregorian.api.Interfaces.FeriadoValidateInterface;

@Service
public class FeriadoEditService {
    
    @Autowired
    private FeriadoValidateInterface feriadoValidate;
    @Autowired
    private FeriadoConverterInterface feriadoConverter;
    @Autowired
    private FeriadoService feriadoService;

    public void edit(FeriadoEditDTO dto, Usuario usuario){
        Feriado feriado = feriadoConverter.toFeriado(dto, usuario);
        feriadoValidate.validate(feriado);
        feriadoService.save(feriado);
    }
}
