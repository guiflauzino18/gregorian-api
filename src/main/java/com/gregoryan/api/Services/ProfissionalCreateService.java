package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Interfaces.ProfissionalConverterInterface;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;

@Service
public class ProfissionalCreateService {

    @Autowired
    private ProfissionalConverterInterface profissionalConverter;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    public Profissional create(ProfissionalCadastroDTO dto, Usuario usuario){

        Profissional profissional = profissionalConverter.toProfissional(dto, usuario); //UsuarioDontExistException -> busca do Usuario pelo login passado

        usuarioValidate.validate(usuario, profissional.getUsuario().getEmpresa()); //AcessoNegadoException;

        return profissionalService.save(profissional);
    }
}
