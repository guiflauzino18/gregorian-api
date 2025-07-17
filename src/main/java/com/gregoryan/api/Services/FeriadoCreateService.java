package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Interfaces.FeriadoValidateInterface;

@Service
public class FeriadoCreateService {
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private FeriadoConverterInterface feriadoConverter;
    @Autowired
    private FeriadoValidateInterface feriadoValidate;

    public void create(FeriadoCadastroDTO dto, Usuario usuario){
        Feriado feriado = feriadoConverter.toFeriado(dto);
        feriadoValidate.validate(feriado); //ConflictException
        feriado.setEmpresa(usuario.getEmpresa());
        feriadoService.save(feriado);
    }
}
