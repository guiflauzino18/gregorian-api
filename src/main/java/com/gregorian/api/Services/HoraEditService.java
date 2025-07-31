package com.gregorian.api.Services;

import com.gregorian.api.DTO.HoraEditDTO;
import com.gregorian.api.Interfaces.HoraListInterface;
import com.gregorian.api.Interfaces.StatusHoraListInterface;
import com.gregorian.api.Models.Hora;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.HorasService;
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
