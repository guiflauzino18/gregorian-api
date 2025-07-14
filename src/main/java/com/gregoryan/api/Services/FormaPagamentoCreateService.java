package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FormaPagamentoCreateDTO;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Services.Crud.FormaPagamentoService;
import com.gregoryan.api.Interfaces.FormaPagamentoConverterInterface;
import com.gregoryan.api.Interfaces.FormaPagamentoValidateInterface;

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
