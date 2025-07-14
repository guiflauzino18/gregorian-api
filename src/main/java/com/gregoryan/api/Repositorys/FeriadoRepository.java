package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;

public interface FeriadoRepository extends JpaRepository<Feriado, Long>{

    Page<Feriado> findByNome(String nome, Pageable pageable);
    Page<Feriado> findByEmpresa(Empresa empresa, Pageable pageable);
    boolean existsByNome(String nome);
}
