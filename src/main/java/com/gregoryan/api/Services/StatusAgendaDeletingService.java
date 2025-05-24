package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Interfaces.StatusAgendaListInterface;

@Service
public class StatusAgendaDeletingService {
    
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusAgendaListInterface statusAgendaList;

    public void delete(long id, Empresa empresa){
        StatusAgenda statusAgenda = statusAgendaList.list(id, empresa);// StatusAgendaDontExistException AcessoNegadoException
        statusAgendaService.delete(statusAgenda);
    }
}
