package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;
@Service
public class DiaEditingService {
    
    @Autowired
    private DiasService service;
    @Autowired
    private DiaConverterInterface diaConverter;

    public void editar(Empresa empresa, DiaEditDTO dto){

        Dias dia = diaConverter.toDia(dto); //DiaDontExistException

        service.save(dia);

    }

}
