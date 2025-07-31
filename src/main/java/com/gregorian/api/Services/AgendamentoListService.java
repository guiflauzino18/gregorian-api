package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Services.Crud.AgendamentoService;
import com.gregorian.api.Interfaces.AgendamentoListInterface;

@Service
public class AgendamentoListService implements AgendamentoListInterface{

    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    @Override
    public Agendamento list(long id, Usuario usuario) {
        var agendamento = agendamentoService.findById(id).orElseThrow(() -> new EntityDontExistException("Agendamento não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, agendamento.getEmpresa());

        return agendamento;
    }
    
}
