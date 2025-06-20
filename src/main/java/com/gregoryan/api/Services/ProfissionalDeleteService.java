package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Interfaces.ProfissionalListInterface;

@Service
public class ProfissionalDeleteService {
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private ProfissionalListInterface profissionalList;

    public void delete(long id, Usuario usuario){
        Profissional profissional = profissionalList.list(id, usuario); //ProfissionalDontExistException
        profissionalService.delete(profissional); 
    }

}
