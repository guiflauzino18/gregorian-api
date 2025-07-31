package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.StatusDia;
import com.gregorian.api.Services.Crud.StatusDiaService;
import com.gregorian.api.Interfaces.StatusDiaListInterface;

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
