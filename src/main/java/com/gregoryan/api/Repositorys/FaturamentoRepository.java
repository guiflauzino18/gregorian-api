package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Faturamento;
import com.gregoryan.api.Models.Paciente;
import java.util.Calendar;


public interface FaturamentoRepository extends JpaRepository<Faturamento, Long>{
    
    Page<Faturamento> findByData(Calendar data, Pageable pageable);
    Page<Faturamento> findByPaciente(Paciente paciente, Pageable pageable);
    Page<Faturamento> findByEmpresa(Empresa empresa, Pageable pageable);
}
