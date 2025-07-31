package com.gregorian.api.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.StatusAtendimento;

public interface StatusAtendimentoRepository extends JpaRepository<StatusAtendimento, Long>{
    
    boolean existsByNome(String nome);
    Optional<StatusAtendimento> findByNome(String nome);
    Optional<StatusAtendimento> findById(long id);
}
