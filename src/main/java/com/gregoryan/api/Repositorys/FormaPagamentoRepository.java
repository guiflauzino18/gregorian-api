package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
    boolean existsByNome(String nome);
    Optional<FormaPagamento> findByNome(String nome);
    Page<FormaPagamento> findByStatus(int status, Pageable pageable);
    Page<FormaPagamento> findByEmpresa(Empresa empresa, Pageable pageable); 
}
