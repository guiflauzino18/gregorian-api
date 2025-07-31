package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.Services.Crud.AgendamentoService;
import com.gregorian.api.Interfaces.AgendamentoListInterface;

@Service
public class AgendamentoDeleteService {
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoListInterface agendamentoList;
    
    public void delete(long id, Usuario usuario){
        var agendamento = agendamentoList.list(id, usuario);
        agendamentoService.delete(agendamento);

    }
}
