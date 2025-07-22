package com.gregoryan.api.Interfaces;

import java.util.List;
import java.util.Optional;

import com.gregoryan.api.Components.AgendaValidateConflict;
import com.gregoryan.api.Components.AgendaValidateDuplicateProfissional;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.Models.Agenda;
import org.springframework.beans.factory.annotation.Autowired;

public interface AgendaValidateInterface {

    void validate(Agenda agenda);
}
