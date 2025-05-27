package com.gregoryan.api.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.Exception.AgendaDontExistException;
import com.gregoryan.api.Exception.AgendaExisteException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.AgendaValidateInterface;

@Service
public class AgendaValidate implements AgendaValidateInterface{

    @Autowired
    private AgendaService agendaService;

    @Override
    public void jaExiste(AgendaCadastroDTO dto) {
        if (agendaService.existsByNome(dto.nome())){
            throw new AgendaExisteException("Já existe uma agenda com este nome");
        }
    }

    @Override
    public void agendaExiste(Optional<Agenda> agenda) {
        if (!agenda.isPresent()){
            throw new AgendaDontExistException("Agenda não encontrada");
        }
    }
    
}
