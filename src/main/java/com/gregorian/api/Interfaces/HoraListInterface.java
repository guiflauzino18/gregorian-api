package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Hora;
import com.gregorian.api.Models.StatusHora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoraListInterface {
    public Hora list(long id);
    public Page<Hora> list(StatusHora status, Pageable pageable);
}
