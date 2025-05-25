package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Interfaces.StatusDiaListInterface;

@Service
public class StatusDiaDeletingService {
    
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusDiaListInterface statusDiaList;

    public void delete(long id, Empresa empresa){
        StatusDia statusDia = statusDiaList.list(id, empresa);

        statusDiaService.delete(statusDia);
    }
}
