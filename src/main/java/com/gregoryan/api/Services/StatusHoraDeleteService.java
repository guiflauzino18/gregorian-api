package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Services.Interfaces.StatusHoraListInterface;
import com.gregoryan.api.Services.Interfaces.StatusHoraValidateInterface;

@Service
public class StatusHoraDeleteService {
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private StatusHoraValidateInterface statusHoraValidate;
    @Autowired
    private StatusHoraListInterface statusHoraList;
    
    public void delete(long id, Empresa empresa){
        statusHoraValidate.deleteNotPermited(id);
        StatusHora statusHora = statusHoraList.list(id, empresa);

        statusHoraService.delete(statusHora);

    }
}
