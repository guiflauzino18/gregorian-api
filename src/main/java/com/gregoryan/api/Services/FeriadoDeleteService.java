package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Interfaces.FeriadoListInterface;

@Service
public class FeriadoDeleteService {
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private FeriadoListInterface feriadoList;

    public void delete(long id, Usuario usuario){
        Feriado feriado = feriadoList.list(id, usuario);
        feriadoService.delete(feriado);
    }
}
