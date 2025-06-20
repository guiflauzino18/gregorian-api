package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Interfaces.StatusAgendaListInterface;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;

@Service
public class StatusAgendaListService implements StatusAgendaListInterface{

    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

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
