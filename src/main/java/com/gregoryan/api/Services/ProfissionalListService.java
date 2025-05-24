package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Interfaces.ProfissionalListInterface;

@Service
public class ProfissionalListService implements ProfissionalListInterface{

    @Autowired
    private ProfissionalService profissionalService;

    @Override
    public Profissional list(long id) {
        return profissionalService.findById(id).orElseThrow(() -> new EntityDontExistException("Profissional não encontrado"));
    }

    @Override
    public Page<Profissional> list(Empresa empresa, Pageable pageable) {
        
        return profissionalService.findByEmpresa(empresa.getId(), pageable);
    }
    
}
