package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Interfaces.ProfissionalConverterInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class ProfissionalEditingService {
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private ProfissionalConverterInterface profissionalConverter;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    public void edit(ProfissionalEditDTO dto, Empresa empresa){
        

        Profissional profissional = profissionalConverter.toProfissional(dto); //ProfissionalDontExitException
        usuarioValidate.isSameEmpresaFromUserLogged(empresa,profissional.getUsuario().getEmpresa());

        profissionalService.save(profissional);

    }
}
