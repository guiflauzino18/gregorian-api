package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.StatusAgenda;
import com.gregorian.api.Services.Crud.StatusAgendaService;
import com.gregorian.api.Interfaces.StatusAgendaListInterface;

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
