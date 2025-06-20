package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Components.StatusHoraValidateConflict;
import com.gregoryan.api.Components.StatusHoraValidateDeleteNotPermited;
import com.gregoryan.api.Models.StatusHora;

import java.util.List;

public interface StatusHoraValidateInterface {
    public static List<StatusHoraValidateInterface> validacoes = List.of(
            new StatusHoraValidateDeleteNotPermited(),
            new StatusHoraValidateConflict()
    );

    void validate(StatusHora statusHora);
}

