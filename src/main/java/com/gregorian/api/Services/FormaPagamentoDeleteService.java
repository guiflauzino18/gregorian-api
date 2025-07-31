package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Services.Crud.FormaPagamentoService;
import com.gregorian.api.Interfaces.FormaPagamentoListInterface;

@Service
public class FormaPagamentoDeleteService {
    @Autowired
    private FormaPagamentoListInterface formaPagamentoList;
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    public void delete(long id, Usuario usuario){
        var formaPagamento = formaPagamentoList.list(id, usuario);
        formaPagamentoService.save(formaPagamento);
    }
}
