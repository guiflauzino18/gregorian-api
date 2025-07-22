package com.gregoryan.api.Services;

import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;

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
