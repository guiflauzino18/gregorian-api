package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Interfaces.ProfissionalConverterInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class ProfissionalCreateService {

    @Autowired
    private ProfissionalConverterInterface profissionalConverter;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    public Profissional create(ProfissionalCadastroDTO dto, Empresa empresa){

        Profissional profissional = profissionalConverter.toProfissional(dto, empresa); //UsuarioDontExistException -> busca do Usuario pelo login passado  

        usuarioValidate.isSameEmpresaFromUserLogged(empresa, profissional.getUsuario().getEmpresa()); //AcessoNegadoException;

        return profissionalService.save(profissional);
    }
}
