package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Interfaces.StatusAgendaConverterInterface;
import com.gregoryan.api.Interfaces.StatusAgendaValidateInterface;

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
