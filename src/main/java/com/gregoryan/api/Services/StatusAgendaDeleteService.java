package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Interfaces.StatusAgendaListInterface;

@Service
public class StatusAgendaDeleteService {
    
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusAgendaListInterface statusAgendaList;

    public void delete(long id, Usuario usuario){
        StatusAgenda statusAgenda = statusAgendaList.list(id, usuario);// StatusAgendaDontExistException AcessoNegadoException
        statusAgendaService.delete(statusAgenda);
    }
}
