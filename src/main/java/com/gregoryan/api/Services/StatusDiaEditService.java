package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Interfaces.StatusDiaValidateInterface;

@Service
public class StatusDiaEditService {
    
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;
    @Autowired
    private StatusDiaValidateInterface statusDiaValidate;
    

    public void edit(StatusDiaEditDTO dto, Usuario usuario){
        StatusDia statusDia = statusDiaConverter.toStatusDia(dto, usuario);
        statusDiaValidate.validate(statusDia);

        statusDiaService.save(statusDia);


    }
}
