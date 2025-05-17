package com.gregoryan.api.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusHora;

public interface StatusHoraRepository extends JpaRepository<StatusHora, Long>{
    Optional<StatusHora> findByNome(String nome);
    boolean existsByNome(String nome);

    Optional<List<StatusHora>> findByEmpresa(Empresa empresa);
}
