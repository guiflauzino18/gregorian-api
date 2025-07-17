package com.gregoryan.api.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Services.Crud.FormaPagamentoService;
import com.gregoryan.api.Interfaces.FormaPagamentoValidateInterface;

@Component
public class FormaPagamentoValidateConflict implements FormaPagamentoValidateInterface{
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Override
    public void validate(FormaPagamento formaPagamento) {
        
        if (formaPagamentoService.existsByNome(formaPagamento.getNome()))
            throw new ConflictException("Já há uma forma de pagamento cadastrada com este nome");

    }
    

}
