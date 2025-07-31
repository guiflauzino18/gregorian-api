package com.gregorian.api.Services;

import com.gregorian.api.Components.StatusHoraValidateConflict;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.StatusHoraCadastroDTO;
import com.gregorian.api.Models.StatusHora;
import com.gregorian.api.Services.Crud.StatusHoraService;
import com.gregorian.api.Interfaces.StatusHoraConverterInterface;

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
