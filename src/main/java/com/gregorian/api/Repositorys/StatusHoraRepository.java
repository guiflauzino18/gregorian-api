package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusHora;

public interface StatusHoraRepository extends JpaRepository<StatusHora, Long>{
    Optional<StatusHora> findByNome(String nome);
    boolean existsByNome(String nome);

    Page<StatusHora> findByEmpresa(Empresa empresa, Pageable pageable);
}
