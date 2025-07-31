package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.FormaPagamentoCreateDTO;
import com.gregorian.api.Models.FormaPagamento;
import com.gregorian.api.Services.Crud.FormaPagamentoService;
import com.gregorian.api.Interfaces.FormaPagamentoConverterInterface;
import com.gregorian.api.Interfaces.FormaPagamentoValidateInterface;

@Service
public class FormaPagamentoCreateService{
    @Autowired
    private FormaPagamentoConverterInterface formaPagamentoConverter;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private FormaPagamentoValidateInterface formaPagamentoValidate;

    public FormaPagamento create(FormaPagamentoCreateDTO dto, Usuario usuario){
        var formaPagamento = formaPagamentoConverter.toFormaPagamento(dto);
        formaPagamentoValidate.validate(formaPagamento);
        formaPagamento.setEmpresa(usuario.getEmpresa());
        return formaPagamentoService.save(formaPagamento);
        
    }
}
