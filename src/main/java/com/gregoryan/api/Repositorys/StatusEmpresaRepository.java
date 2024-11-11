package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.StatusEmpresa;

public interface StatusEmpresaRepository extends JpaRepository<StatusEmpresa, Long>{
    
    boolean existsByNome(String nome);
    Page<StatusEmpresa> findByNome(String nome, Pageable pageable);

}
