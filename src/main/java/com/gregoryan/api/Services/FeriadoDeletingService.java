package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Services.Interfaces.FeriadoListInterface;

@Service
public class FeriadoDeletingService {
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private FeriadoListInterface feriadoList;

    public void delete(long id,Empresa empresa){
        Feriado feriado = feriadoList.list(id, empresa);
        feriadoService.delete(feriado);
    }
}
