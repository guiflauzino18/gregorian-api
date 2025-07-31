package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.StatusDiaCadastroDTO;
import com.gregorian.api.Models.StatusDia;
import com.gregorian.api.Services.Crud.StatusDiaService;
import com.gregorian.api.Interfaces.StatusDiaConverterInterface;
import com.gregorian.api.Interfaces.StatusDiaValidateInterface;

@Service
public class StatusDiaCreateService {
    
    @Autowired
    private StatusDiaValidateInterface statusDiaValidate;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;
    @Autowired
    private StatusDiaService statusDiaService;

    public void create(StatusDiaCadastroDTO dto, Usuario usuario){
        StatusDia statusDia = statusDiaConverter.toStatusDia(dto);
        statusDiaValidate.validate(statusDia); //ConflictException
        statusDia.setEmpresa(usuario.getEmpresa());
        statusDiaService.save(statusDia);
    }
}
