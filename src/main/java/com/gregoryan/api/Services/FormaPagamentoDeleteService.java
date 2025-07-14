package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Services.Crud.FormaPagamentoService;
import com.gregoryan.api.Interfaces.FormaPagamentoListInterface;

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
