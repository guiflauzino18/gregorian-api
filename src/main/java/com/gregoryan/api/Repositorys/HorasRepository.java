package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Horas;
import com.gregoryan.api.Models.StatusHora;

public interface HorasRepository extends JpaRepository<Horas, Long>{

    Page<Horas> findByStatusHora(StatusHora status, Pageable pageable);
   
    
}
