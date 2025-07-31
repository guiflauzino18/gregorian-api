package com.gregorian.api.Repositorys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    
    Page<Paciente> findByNome(String nome, Pageable pageable);
    Page<Paciente> findBySobrenome(String sobrenome, Pageable pageable);
    Optional<Paciente> findByCpf(long cpf);
    Page<Paciente> findByTelefone(long telefone, Pageable pageable);
    Page<Paciente> findByEmpresa(Empresa empresa, Pageable pageable);
    boolean existsByCpf(long cpf);

}
