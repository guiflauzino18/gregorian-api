package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;

public interface StatusAgendaRepository extends JpaRepository<StatusAgenda, Long>{
    
    boolean existsByNome(String nome);
    Optional<StatusAgenda> findByNome(String nome);
    Page<StatusAgenda> findByEmpresa(Empresa empresa, Pageable pageable);
}
