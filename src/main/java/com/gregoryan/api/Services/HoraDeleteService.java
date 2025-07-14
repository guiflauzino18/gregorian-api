package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Interfaces.HoraListInterface;

@Service
public class HoraDeleteService {
    @Autowired
    private HoraListInterface horaList;
    @Autowired
    private HorasService horaService;

    //TODO Deleta hora do banco de dados.
    public void delete(long id){
        var hora = horaList.list(id);
        horaService.delete(hora);
    }
}
