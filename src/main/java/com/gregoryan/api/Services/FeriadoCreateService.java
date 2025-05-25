package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Services.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Services.Interfaces.FeriadoValidateInterface;

@Service
public class FeriadoCreateService {
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private FeriadoConverterInterface feriadoConverter;
    @Autowired
    private FeriadoValidateInterface feriadoValidate;

    public void create(FeriadoCadastroDTO dto, Empresa empresa){
        feriadoValidate.jaExiste(dto.nome()); //ConflictException
        Feriado feriado = feriadoConverter.toFeriado(dto);
        feriado.setEmpresa(empresa);

        feriadoService.save(feriado);
    }
}
