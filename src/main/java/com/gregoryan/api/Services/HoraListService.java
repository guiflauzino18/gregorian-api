package com.gregoryan.api.Services;

import com.gregoryan.api.Models.StatusHora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Hora;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Interfaces.HoraListInterface;

@Service
public class HoraListService implements HoraListInterface{

    @Autowired
    private HorasService horaService;

    @Override
    public Hora list(long id) {
        return horaService.findById(id).orElseThrow(() -> new EntityDontExistException("Hora não encontrada"));

    }

    @Override
    public Page<Hora> list(StatusHora status, Pageable pageable) {
        return horaService.findByStatusHora(status, pageable);
    }

}
