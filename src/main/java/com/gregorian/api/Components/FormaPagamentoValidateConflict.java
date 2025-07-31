package com.gregorian.api.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gregorian.api.Exception.ConflictException;
import com.gregorian.api.Models.FormaPagamento;
import com.gregorian.api.Services.Crud.FormaPagamentoService;
import com.gregorian.api.Interfaces.FormaPagamentoValidateInterface;

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
