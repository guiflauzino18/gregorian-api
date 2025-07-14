package com.gregoryan.api.Components;

import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Interfaces.StatusHoraValidateInterface;
import org.springframework.stereotype.Component;

@Component
public class StatusHoraValidateDeleteNotPermited implements StatusHoraValidateInterface {

    @Override
    public void validate(StatusHora statusHora) {
        if (statusHora.getId() == 1 || statusHora.getId() == 2){
            throw new AcessoNegadoException("Este status não pode ser excluído");
        }
    }
}
