package com.gregoryan.api.Repositorys;

import com.gregoryan.api.Models.Hora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.StatusHora;

public interface HorasRepository extends JpaRepository<Hora, Long>{

    Page<Hora> findByStatusHora(StatusHora status, Pageable pageable);

}
