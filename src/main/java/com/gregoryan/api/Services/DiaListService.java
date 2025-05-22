package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaResponseDTO;
import com.gregoryan.api.Exception.DiaDontExistException;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.DiaListInterface;

@Service
public class DiaListService implements DiaListInterface{
    
    @Autowired
    private DiasService service;
    @Autowired
    private DiaConverterInterface diaConverter;

    @Override
    public Dias list(long id) {
        
        return service.findById(id).orElseThrow(() -> new DiaDontExistException("Dia não encontrado"));
    }

    @Override
    public Page<Dias> list(Empresa empresa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }
}
