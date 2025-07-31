package com.gregorian.api.Repositorys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Models.Atendimento;
import com.gregorian.api.Models.StatusAtendimento;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long>{
    
    Page<Atendimento> findByStatusAtendimento(StatusAtendimento statusAtendimento, Pageable pageable);

    Optional<Atendimento> findByAgendamento(Agendamento agendamento);
}
