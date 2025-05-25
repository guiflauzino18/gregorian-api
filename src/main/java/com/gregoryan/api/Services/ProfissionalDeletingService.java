package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Interfaces.ProfissionalListInterface;

@Service
public class ProfissionalDeletingService {
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private ProfissionalListInterface profissionalList;

    public void delete(long id, Empresa empresa){
        Profissional profissional = profissionalList.list(id, empresa); //ProfissionalDontExistException
        profissionalService.delete(profissional); 
    }

}
