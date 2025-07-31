package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusDia;

public interface StatusDiaRepository extends JpaRepository<StatusDia, Long>{

    boolean existsByNome(String nome);
    Optional<StatusDia> findByNome(String nome);
    Page<StatusDia> findByEmpresa(Empresa empresa, Pageable pageable);
    
}
