package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Feriado;

public interface FeriadoRepository extends JpaRepository<Feriado, Long>{

    Page<Feriado> findByNome(String nome, Pageable pageable);
    Page<Feriado> findByEmpresa(Empresa empresa, Pageable pageable);
    boolean existsByNome(String nome);
}
