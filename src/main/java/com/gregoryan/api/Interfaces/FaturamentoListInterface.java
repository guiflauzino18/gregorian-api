package com.gregoryan.api.Interfaces;

import com.gregoryan.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Faturamento;

public interface FaturamentoListInterface {
    Faturamento list (long id, Usuario usuario);
    Page<Faturamento> list (Empresa empresa, Pageable pageable);
}
