package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Components.StatusHoraValidateConflict;
import com.gregoryan.api.Components.StatusHoraValidateDeleteNotPermited;
import com.gregoryan.api.Models.StatusHora;

import java.util.List;

public interface StatusHoraValidateInterface {

    void validate(StatusHora statusHora);
}

