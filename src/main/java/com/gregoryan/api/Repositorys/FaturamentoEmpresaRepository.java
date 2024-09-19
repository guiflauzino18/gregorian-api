package com.gregoryan.api.Repositorys;

import java.util.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.FaturamentoEmpresa;

public interface FaturamentoEmpresaRepository extends JpaRepository<FaturamentoEmpresa, Long>{

    Page<FaturamentoEmpresa> findByEmpresa(Empresa empresa, Pageable pageable);
    Page<FaturamentoEmpresa> findByData(Calendar data, Pageable pageable);
}
