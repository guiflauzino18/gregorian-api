package com.gregoryan.api.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.StatusAtendimento;

public interface StatusAtendimentoRepository extends JpaRepository<StatusAtendimento, Long>{
    
    boolean existsByNome(String nome);
    Optional<StatusAtendimento> findByNome(String nome);
}
