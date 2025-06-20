package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Interfaces.FeriadoValidateInterface;

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
