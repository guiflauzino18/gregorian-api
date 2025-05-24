package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class AgendaDeletingService {

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    public void deletar(Empresa empresa, long idAgenda){

        Agenda agenda = agendaService.findById(idAgenda).orElseThrow(() -> new EntityDontExistException("Agenda não encontrada"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, agenda.getEmpresa());

        agendaService.delete(agenda);

    }
    
}
