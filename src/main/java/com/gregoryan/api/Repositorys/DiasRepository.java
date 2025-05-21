package com.gregoryan.api.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Dias;

public interface DiasRepository extends JpaRepository<Dias, Long>{
    boolean existsByNome(String nome);
    Optional<Dias> findByNome(String nome);
    
    //Busca agenda do dia 
    @Query(value = "SELECT agenda.id from tbl_dias dia right join tbl_agenda agenda on (agenda.id = dia.agenda_fk) where dia.id = :idDia", nativeQuery = true)
    Optional<Long> getAgenda(long idDia);
    /*
     *     @Query(value = "SELECT*FROM tbl_usuario WHERE login = :login AND senha = MD5(:senha)", nativeQuery = true)
     *     public Usuario loginValidation(String login, String senha);
     */
}
