package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Faturamento;

public interface FaturamentoListInterface {
    Faturamento list (long id, Usuario usuario);
    Page<Faturamento> list (Empresa empresa, Pageable pageable);
}
