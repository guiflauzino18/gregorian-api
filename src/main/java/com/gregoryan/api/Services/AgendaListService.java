package com.gregoryan.api.Services;


import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Interfaces.AgendaListInterface;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;


@Service
public class AgendaListService implements AgendaListInterface{

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidate;

    //Lista agenda por ID
    @Override
    public Agenda list(long id, Usuario usuario) {
        
        Agenda agenda = agendaService.findById(id).orElseThrow(() -> new EntityDontExistException("Agenda não encontrada"));
        usuarioValidate.validate(usuario, agenda.getEmpresa());

        return agenda;

    }

    //Lista agenda por empresa
    @Override
    public Page<Agenda> list(Empresa empresa, Pageable pageable) {
        return agendaService.findByEmpresa(empresa, pageable);
        
    }
    
}
