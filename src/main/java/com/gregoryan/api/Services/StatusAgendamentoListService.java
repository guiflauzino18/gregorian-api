package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.StatusAgendamento;
import com.gregoryan.api.Services.Crud.StatusAgendamentoService;
import com.gregoryan.api.Interfaces.StatusAgendamentoListInterface;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;

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
