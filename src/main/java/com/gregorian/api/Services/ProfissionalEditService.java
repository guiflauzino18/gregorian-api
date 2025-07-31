package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.ProfissionalEditDTO;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Services.Crud.ProfissionalService;
import com.gregorian.api.Interfaces.ProfissionalConverterInterface;

@Service
public class ProfissionalEditService {
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private ProfissionalConverterInterface profissionalConverter;

    public void edit(ProfissionalEditDTO dto, Usuario usuario){
        
        Profissional profissional = profissionalConverter.toProfissional(dto, usuario); //ProfissionalDontExitException
        profissionalService.save(profissional);

    }
}
