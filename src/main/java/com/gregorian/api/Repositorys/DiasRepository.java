package com.gregorian.api.Repositorys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gregorian.api.Models.Dias;
import com.gregorian.api.Models.Empresa;

public interface DiasRepository extends JpaRepository<Dias, Long>{
    boolean existsByNome(String nome);
    Optional<Dias> findByNome(String nome);
    Page<Dias> findByEmpresa(Empresa empresa, Pageable pageable);
    
    //Busca agenda do dia 
    @Query(value = "SELECT agenda.id from tbl_dias dia right join tbl_agenda agenda on (agenda.id = dia.agenda_fk) where dia.id = :idDia", nativeQuery = true)
    Optional<Long> getAgenda(long idDia);
}
