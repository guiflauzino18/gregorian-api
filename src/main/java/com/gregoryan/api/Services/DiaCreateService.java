package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaListInterface;
import com.gregoryan.api.Services.Interfaces.StatusHoraListInterface;

@Service
public class DiaCreateService {
    
    @Autowired
    private DiasService service;
    @Autowired
    private DiaConverterInterface diaConverter;
    @Autowired
    private StatusDiaListInterface statusDiaList;
    @Autowired HorasService horasService;
    @Autowired
    private StatusHoraListInterface statusHoraList;

    public Dias create(DiaCadastroDTO dto, Empresa empresa){

        Dias dia = diaConverter.toDia(dto);
        dia.setEmpresa(empresa);
        StatusDia statusDia = statusDiaList.list("Ativo", empresa);
        dia.setStatusDia(statusDia);

        StatusHora statusHora = statusHoraList.list("Ativo", empresa);
        dia.createHoras(statusHora, horasService);

        return service.save(dia);

    }
}
