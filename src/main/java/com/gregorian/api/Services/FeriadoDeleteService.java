package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.Feriado;
import com.gregorian.api.Services.Crud.FeriadoService;
import com.gregorian.api.Interfaces.FeriadoListInterface;

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
