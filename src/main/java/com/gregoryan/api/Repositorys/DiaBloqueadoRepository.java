package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.DiaBloqueado;

public interface DiaBloqueadoRepository extends JpaRepository<DiaBloqueado, Long>{
    
    Page<DiaBloqueado> findByNome(String nome, Pageable pageable);
}