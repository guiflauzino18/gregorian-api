package com.gregoryan.api.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
}
