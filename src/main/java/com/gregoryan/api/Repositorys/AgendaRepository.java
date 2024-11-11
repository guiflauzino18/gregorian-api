package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    Page<Agenda> findByStatusAgenda(StatusAgenda statusAgenda, Pageable pageable);
    Page<Agenda> findByEmpresa(Empresa empresa, Pageable pageable);
    boolean existsByNome(String nome);
    Optional<Agenda> findByNome(String nome);
}
