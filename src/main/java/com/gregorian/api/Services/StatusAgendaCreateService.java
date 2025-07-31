package com.gregorian.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.StatusAgendaCadastroDTO;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusAgenda;
import com.gregorian.api.Services.Crud.StatusAgendaService;
import com.gregorian.api.Interfaces.StatusAgendaConverterInterface;
import com.gregorian.api.Interfaces.StatusAgendaValidateInterface;

@Service
public class StatusAgendaCreateService {
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusAgendaConverterInterface statusAgendaConverter;
    @Autowired
    private StatusAgendaValidateInterface statusAgendaValidate;

    public void create(StatusAgendaCadastroDTO dto, Empresa empresa){
        StatusAgenda statusAgenda = statusAgendaConverter.toStatusAgenda(dto);
        statusAgendaValidate.validate(statusAgenda);
        statusAgenda.setEmpresa(empresa);
        statusAgendaService.save(statusAgenda);
    }
}
