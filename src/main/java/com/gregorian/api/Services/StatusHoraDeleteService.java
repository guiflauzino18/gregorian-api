package com.gregorian.api.Services;

import com.gregorian.api.Components.StatusHoraValidateDeleteNotPermited;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.StatusHora;
import com.gregorian.api.Services.Crud.StatusHoraService;
import com.gregorian.api.Interfaces.StatusHoraListInterface;

@Service
public class StatusHoraDeleteService {
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private StatusHoraListInterface statusHoraList;
    @Autowired
    private StatusHoraValidateDeleteNotPermited validateDelete;

    public void delete(long id, Usuario usuario){
        StatusHora statusHora = statusHoraList.list(id, usuario);
        validateDelete.validate(statusHora);
        statusHoraService.delete(statusHora);
    }
}
