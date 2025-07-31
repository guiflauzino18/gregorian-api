package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Models.Empresa;

import java.util.Calendar;
import java.util.Optional;

public interface DiaBloqueadoRepository extends JpaRepository<DiaBloqueado, Long>{
    
    Page<DiaBloqueado> findByNome(String nome, Pageable pageable);
    Page<DiaBloqueado> findByEmpresa(Empresa empresa, Pageable pageable);
    Optional<DiaBloqueado> findByDia(Calendar dia);
    
}
