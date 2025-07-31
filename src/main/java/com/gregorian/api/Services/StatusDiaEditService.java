package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.StatusDiaEditDTO;
import com.gregorian.api.Models.StatusDia;
import com.gregorian.api.Services.Crud.StatusDiaService;
import com.gregorian.api.Interfaces.StatusDiaConverterInterface;
import com.gregorian.api.Interfaces.StatusDiaValidateInterface;

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
