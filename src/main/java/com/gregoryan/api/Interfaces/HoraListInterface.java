package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Hora;
import com.gregoryan.api.Models.StatusHora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoraListInterface {
    public Hora list(long id);
    public Page<Hora> list(StatusHora status, Pageable pageable);
}
