package com.gregorian.api.Repositorys;

import com.gregorian.api.Models.Hora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.StatusHora;

public interface HorasRepository extends JpaRepository<Hora, Long>{

    Page<Hora> findByStatusHora(StatusHora status, Pageable pageable);

}
