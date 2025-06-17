package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Services.Interfaces.AgendamentoListInterface;

@Service
public class AgendamentoListService implements AgendamentoListInterface{

    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private UsuarioValidate usuarioValidate;

    @Override
    public Agendamento list(long id, Empresa empresa) {
        var agendamento = agendamentoService.findById(id).orElseThrow(() -> new EntityDontExistException("Agendamento não encontrado"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, agendamento.getEmpresa());

        return agendamento;
    }
    
}
