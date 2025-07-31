package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.FeriadoCadastroDTO;
import com.gregorian.api.Models.Feriado;
import com.gregorian.api.Services.Crud.FeriadoService;
import com.gregorian.api.Interfaces.FeriadoConverterInterface;
import com.gregorian.api.Interfaces.FeriadoValidateInterface;

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
