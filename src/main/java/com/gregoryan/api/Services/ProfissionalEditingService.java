package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Interfaces.ProfissionalConverterInterface;

@Service
public class ProfissionalEditingService {
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private ProfissionalConverterInterface profissionalConverter;

    public void edit(ProfissionalEditDTO dto, Usuario usuario){
        
        Profissional profissional = profissionalConverter.toProfissional(dto, usuario); //ProfissionalDontExitException
        profissionalService.save(profissional);

    }
}
