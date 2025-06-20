package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Interfaces.AgendamentoListInterface;

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
