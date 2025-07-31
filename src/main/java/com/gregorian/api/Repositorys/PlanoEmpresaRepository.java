package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.PlanoEmpresa;

public interface PlanoEmpresaRepository extends JpaRepository<PlanoEmpresa, Long>{

    Page<PlanoEmpresa> findByNome(String nome, Pageable pageable);
    boolean existsByValor(float valor);
    
}
