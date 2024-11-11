package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.PlanoPaciente;

public interface PlanoPacienteRepository extends JpaRepository<PlanoPaciente, Long>{
    
    Page<PlanoPaciente> findByEmpresa(Empresa empresa, Pageable pageable);
    Page<PlanoPaciente> findByNome(String nome, Pageable pageable);


}
