package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Services.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Services.Interfaces.FeriadoValidateInterface;

@Service
public class FeriadoEditingService {
    
    @Autowired
    private FeriadoValidateInterface feriadoValidate;
    @Autowired
    private FeriadoConverterInterface feriadoConverter;
    @Autowired
    private FeriadoService feriadoService;

    public void edit(FeriadoEditDTO dto, Empresa empresa){
        feriadoValidate.jaExiste(dto.nome());
        Feriado feriado = feriadoConverter.toFeriado(dto, empresa);

        feriadoService.save(feriado);
    }
}
