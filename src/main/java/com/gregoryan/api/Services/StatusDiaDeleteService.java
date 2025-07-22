package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Interfaces.StatusDiaListInterface;

@Service
public class StatusDiaDeleteService {
    
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusDiaListInterface statusDiaList;

    public void delete(long id, Usuario usuario){
        StatusDia statusDia = statusDiaList.list(id, usuario);

        statusDiaService.delete(statusDia);
    }
}
