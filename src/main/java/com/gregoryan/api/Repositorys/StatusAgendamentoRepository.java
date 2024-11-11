package com.gregoryan.api.Repositorys;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gregoryan.api.Models.StatusAgendamento;

public interface StatusAgendamentoRepository extends JpaRepository<StatusAgendamento, Long>{

    boolean existsByNome(String nome);
    Optional<StatusAgendamento> findByNome(String nome);
    
}
