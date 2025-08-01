package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.ProfissionalCadastroDTO;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Services.Crud.ProfissionalService;
import com.gregorian.api.Interfaces.ProfissionalConverterInterface;

@Service
public class ProfissionalCreateService {

    @Autowired
    private ProfissionalConverterInterface profissionalConverter;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidate;

    public Profissional create(ProfissionalCadastroDTO dto, Usuario usuario){

        Profissional profissional = profissionalConverter.toProfissional(dto, usuario); //UsuarioDontExistException -> busca do Usuario pelo login passado
        usuarioValidate.validate(usuario, profissional.getUsuario().getEmpresa()); //AcessoNegadoException;
        return profissionalService.save(profissional);
    }
}
