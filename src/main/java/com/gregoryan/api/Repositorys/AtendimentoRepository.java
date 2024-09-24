package com.gregoryan.api.Repositorys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Atendimento;
import com.gregoryan.api.Models.StatusAtendimento;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long>{
    
    Page<Atendimento> findByStatusAtendimento(StatusAtendimento statusAtendimento, Pageable pageable);

    Optional<Atendimento> findByAgendamento(Agendamento agendamento);
}
