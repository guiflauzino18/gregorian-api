package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaValidateInterface;

@Service
public class StatusDiaEditingService {
    
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;
    @Autowired
    private StatusDiaValidateInterface statusDiaValidate;
    

    public void edit(StatusDiaEditDTO dto, Empresa empresa){
        statusDiaValidate.jaExiste(dto.nome());
        StatusDia statusDia = statusDiaConverter.toStatusDia(dto, empresa);

        statusDiaService.save(statusDia);


    }
}
