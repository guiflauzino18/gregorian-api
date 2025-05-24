package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Interfaces.StatusAgendaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaValidateInterface;

@Service
public class StatusAgendaCreateService {
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusAgendaConverterInterface statusAgendaConverter;
    @Autowired
    private StatusAgendaValidateInterface statusAgendaValidate;

    public void create(StatusAgendaCadastroDTO dto, Empresa empresa){
        statusAgendaValidate.jaExiste(dto.nome());
        StatusAgenda statusAgenda = statusAgendaConverter.toStatusAgenda(dto);
        statusAgenda.setEmpresa(empresa);
        statusAgendaService.save(statusAgenda);
    }
}
