package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.PlanoPaciente;

public interface PlanoPacienteRepository extends JpaRepository<PlanoPaciente, Long>{
    
    Page<PlanoPaciente> findByEmpresa(Empresa empresa, Pageable pageable);
    Page<PlanoPaciente> findByNome(String nome, Pageable pageable);


}
