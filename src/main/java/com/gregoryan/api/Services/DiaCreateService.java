package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;

@Service
public class DiaCreateService {
    
    @Autowired
    private DiasService service;
    @Autowired
    private DiaConverterInterface diaConverter;

    public Dias create(DiaCadastroDTO dto){

        Dias dia = diaConverter.toDia(dto);

        return service.save(dia);


    }
}
