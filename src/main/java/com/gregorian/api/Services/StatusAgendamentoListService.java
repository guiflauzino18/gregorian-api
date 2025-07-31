package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.StatusAgendamento;
import com.gregorian.api.Services.Crud.StatusAgendamentoService;
import com.gregorian.api.Interfaces.StatusAgendamentoListInterface;
import com.gregorian.api.Interfaces.UsuarioValidateInterface;

@Service
public class StatusAgendamentoListService implements StatusAgendamentoListInterface{

    @Autowired
    private StatusAgendamentoService statusAgendamentoService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    @Override
    public StatusAgendamento list(long id, Usuario usuario) {
        var status = statusAgendamentoService.findById(id).orElseThrow(() -> new EntityDontExistException("Status de Agendamento não encontrado"));
        usuarioValidate.validate(usuario, status.getEmpresa());

        return status;
    }
    
}
