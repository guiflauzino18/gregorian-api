package com.gregoryan.api.Services;

import com.gregoryan.api.Components.StatusHoraValidateConflict;
import com.gregoryan.api.Components.StatusHoraValidateDeleteNotPermited;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Interfaces.StatusHoraConverterInterface;
import com.gregoryan.api.Interfaces.StatusHoraValidateInterface;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusHoraCreateService {
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private StatusHoraConverterInterface statusHoraConverter;
    @Autowired
    private StatusHoraValidateConflict validateConflict;

    public void create(StatusHoraCadastroDTO dto, Usuario usuario){

        StatusHora statusHora = statusHoraConverter.toStatusHora(dto);
        validateConflict.validate(statusHora);
        statusHora.setEmpresa(usuario.getEmpresa());
        statusHoraService.save(statusHora);
    }
}
