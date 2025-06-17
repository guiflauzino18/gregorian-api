package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Services.Interfaces.AgendamentoListInterface;

@Service
public class AgendamentoDeleteService {
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoListInterface agendamentoList;
    
    public void delete(long id, Empresa empresa){
        var agendamento = agendamentoList.list(id, empresa);
        agendamentoService.delete(agendamento);

    }
}
