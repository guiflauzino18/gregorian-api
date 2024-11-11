package com.gregoryan.api.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.gregoryan.api.Models.StatusDia;

public interface StatusDiaRepository extends JpaRepository<StatusDia, Long>{

    boolean existsByNome(String nome);
    Optional<StatusDia> findByNome(String nome);
    
}
