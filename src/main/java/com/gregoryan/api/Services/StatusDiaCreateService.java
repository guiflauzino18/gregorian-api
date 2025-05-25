package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaValidateInterface;

@Service
public class StatusDiaCreateService {
    
    @Autowired
    private StatusDiaValidateInterface statusDiaValidate;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;
    @Autowired
    private StatusDiaService statusDiaService;

    public void create(StatusDiaCadastroDTO dto, Empresa empresa){
        statusDiaValidate.jaExiste(dto.nome()); //ConflictException
        StatusDia statusDia = statusDiaConverter.toStatusDia(dto);
        statusDia.setEmpresa(empresa);
        statusDiaService.save(statusDia);
    }
}
