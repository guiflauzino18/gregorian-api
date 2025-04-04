package com.gregoryan.api.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
    Optional<Empresa> findByCnpj(long cnpj);

    boolean existsByCnpj(long cnpj);

}
