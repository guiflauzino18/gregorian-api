package com.gregorian.api.Services;

import com.gregorian.api.Models.Dias;
import com.gregorian.api.Models.StatusDia;
import com.gregorian.api.Models.StatusHora;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.DiaCadastroDTO;
import com.gregorian.api.Services.Crud.DiasService;
import com.gregorian.api.Services.Crud.HorasService;
import com.gregorian.api.Interfaces.DiaConverterInterface;
import com.gregorian.api.Interfaces.StatusDiaListInterface;
import com.gregorian.api.Interfaces.StatusHoraListInterface;

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
