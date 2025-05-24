package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Services.Interfaces.StatusHoraConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusHoraValidateInterface;

@Service
public class StatusHoraCreateService {
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private StatusHoraConverterInterface statusHoraConverter;
    @Autowired
    private StatusHoraValidateInterface statusHoraValidate;


    public void create(StatusHoraCadastroDTO dto, Empresa empresa){

        statusHoraValidate.jaExiste(dto.nome());

        StatusHora statusHora = statusHoraConverter.toStatusHora(dto);
        statusHora.setEmpresa(empresa);

        statusHoraService.save(statusHora);
        
    }
}
