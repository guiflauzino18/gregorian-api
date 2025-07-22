package com.gregoryan.api.Services;

import com.gregoryan.api.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Interfaces.StatusDiaListInterface;
import com.gregoryan.api.Interfaces.StatusHoraListInterface;

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

    public Dias create(DiaCadastroDTO dto, Usuario usuario){

        Dias dia = diaConverter.toDia(dto);
        dia.setEmpresa(usuario.getEmpresa());
        StatusDia statusDia = statusDiaList.list("Ativo", usuario);
        dia.setStatusDia(statusDia);

        StatusHora statusHora = statusHoraList.list("Ativo", usuario);
        dia.createHoras(statusHora, horasService);

        return service.save(dia);

    }
}
