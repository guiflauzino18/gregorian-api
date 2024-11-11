package com.gregoryan.api.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Dias;

public interface DiasRepository extends JpaRepository<Dias, Long>{
    boolean existsByNome(String nome);
    Optional<Dias> findByNome(String nome);
}
