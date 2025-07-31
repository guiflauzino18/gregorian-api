package com.gregorian.api.Repositorys;

import com.gregorian.api.Models.Profissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusAgenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    Page<Agenda> findByStatusAgenda(StatusAgenda statusAgenda, Pageable pageable);
    Page<Agenda> findByEmpresa(Empresa empresa, Pageable pageable);
    boolean existsByNome(String nome);
    Optional<Agenda> findByNome(String nome);
    Optional<Agenda> findByProfissional(Profissional profissional);
    boolean existsByProfissional(Profissional profissional);
}
