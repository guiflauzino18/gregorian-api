package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Services.Crud.ProfissionalService;
import com.gregorian.api.Interfaces.ProfissionalListInterface;

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
