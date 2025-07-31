package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Services.Crud.AgendaService;

@Service
public class AgendaDeleteService {

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidate;

    public void deletar(Usuario usuario, long idAgenda){

        Agenda agenda = agendaService.findById(idAgenda).orElseThrow(() -> new EntityDontExistException("Agenda não encontrada"));
        usuarioValidate.validate(usuario, agenda.getEmpresa());

        agendaService.delete(agenda);

    }
    
}
