package com.gregoryan.api.Services.Interfaces;

import java.util.Optional;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.Models.Agenda;

public interface AgendaValidateInterface {
    void jaExiste(AgendaCadastroDTO dto);
    void agendaExiste(Optional<Agenda> agenda);
}
