package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Paciente;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.StatusAgendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
    
    Page<Agendamento> findByProfissional(Profissional profissional, Pageable pageable);
    Page<Agendamento> findByPaciente(Paciente paciente, Pageable pageable);
    Page<Agendamento> findByEmpresa(Empresa empresa, Pageable pageable);
    Page<Agendamento> findByStatusAgendamento(StatusAgendamento statusAgendamento, Pageable pageable);
}
