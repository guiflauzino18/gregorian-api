package com.gregorian.api.Components;

import com.gregorian.api.Exception.AcessoNegadoException;
import com.gregorian.api.Models.StatusHora;
import com.gregorian.api.Interfaces.StatusHoraValidateInterface;
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
