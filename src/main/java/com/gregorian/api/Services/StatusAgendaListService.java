package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusAgenda;
import com.gregorian.api.Services.Crud.StatusAgendaService;
import com.gregorian.api.Interfaces.StatusAgendaListInterface;

@Service
public class StatusAgendaListService implements StatusAgendaListInterface{

    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidate;

    @Override
    public StatusAgenda list(long id, Usuario usuario) {
        StatusAgenda statusAgenda = statusAgendaService.findById(id).orElseThrow(() -> new EntityDontExistException("Status Agenda não encontrado"));
        System.out.println(statusAgenda.getNome());
        usuarioValidate.validate(usuario, statusAgenda.getEmpresa());

        return statusAgenda;
    }

    @Override
    public Page<StatusAgenda> list(Empresa empresa, Pageable pageable) {
        return statusAgendaService.findByEmpresa(empresa, pageable);
    }

    @Override
    public StatusAgenda list(String nome, Usuario usuario) {
        StatusAgenda statusAgenda = statusAgendaService.findByNome(nome).orElseThrow(() -> new EntityDontExistException("Status da Agenda não encontrado"));
        usuarioValidate.validate(usuario, statusAgenda.getEmpresa());
        return statusAgenda;
    }
    
}
