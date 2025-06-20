package com.gregoryan.api.Services;

import com.gregoryan.api.Components.StatusHoraValidateConflict;
import com.gregoryan.api.Components.StatusHoraValidateDeleteNotPermited;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Interfaces.StatusHoraListInterface;
import com.gregoryan.api.Interfaces.StatusHoraValidateInterface;

import java.util.List;

@Service
public class StatusHoraDeleteService {
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private StatusHoraListInterface statusHoraList;

    public void delete(long id, Usuario usuario){
        StatusHora statusHora = statusHoraList.list(id, usuario);
        validate(statusHora);
        statusHoraService.delete(statusHora);

    }

    private void validate(StatusHora statusHora){
        for (StatusHoraValidateInterface validacao : StatusHoraValidateInterface.validacoes){
            validacao.validate(statusHora);
        }
    }
}
