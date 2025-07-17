package com.gregoryan.api.Services;

import com.gregoryan.api.DTO.HoraEditDTO;
import com.gregoryan.api.Interfaces.HoraListInterface;
import com.gregoryan.api.Interfaces.StatusHoraListInterface;
import com.gregoryan.api.Models.Hora;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.HorasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoraEditService {

    @Autowired
    private HoraListInterface horaList;
    @Autowired
    private HorasService horaService;
    @Autowired
    private StatusHoraListInterface statusHoraList;

    public Hora edit(HoraEditDTO dto, Usuario usuarioLogado){
        var hora = horaList.list(dto.idHora());
        var status = statusHoraList.list(dto.idStatusHora(), usuarioLogado);
        hora.setStatusHora(status);
        return horaService.save(hora);
    }
}
