package com.gregorian.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.Services.Crud.HorasService;
import com.gregorian.api.Interfaces.HoraListInterface;

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
