package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Interfaces.StatusDiaValidateInterface;

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
