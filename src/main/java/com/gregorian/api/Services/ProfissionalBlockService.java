package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Interfaces.ProfissionalListInterface;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.ProfissionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalBlockService {

    @Autowired
    private ProfissionalService service;
    @Autowired
    private ProfissionalListInterface list;
    @Autowired
    private UsuarioValidateIsNotYourProperties validate;

    private Logger logger = LoggerFactory.getLogger(ProfissionalBlockService.class);

    public void block(long id, Usuario usuarioLogado){
        var profissional = list.list(id, usuarioLogado);
        validate.validate(usuarioLogado, profissional.getUsuario().getEmpresa());

        String acao = "";
        if (profissional.getStatus() == Profissional.StatusProfissional.ATIVO) {
            profissional.setStatus(Profissional.StatusProfissional.BLOQUEADO);
            acao = "bloqueou";
        }

        else if (profissional.getStatus() == Profissional.StatusProfissional.BLOQUEADO){

            profissional.setStatus(Profissional.StatusProfissional.ATIVO);
            acao = "desbloqueou";
        }

        service.save(profissional);
        logger.info("update: %s %s o profissional %s".formatted(usuarioLogado, acao, profissional.getUsuario()));
    }
}
