package com.gregorian.api.Interfaces;

import com.gregorian.api.Models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.FormaPagamento;

public interface FormaPagamentoListInterface {
    FormaPagamento list(long id, Usuario usuario);
    Page<FormaPagamento> list(Empresa empresa, Pageable pageable);
}
