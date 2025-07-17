package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FormaPagamentoEditDTO;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Services.Crud.FormaPagamentoService;
import com.gregoryan.api.Interfaces.FormaPagamentoConverterInterface;
import com.gregoryan.api.Interfaces.FormaPagamentoListInterface;
import com.gregoryan.api.Interfaces.FormaPagamentoValidateInterface;

@Service
public class FormaPagamentoEditService {
    @Autowired
    private FormaPagamentoListInterface formaPagamentoList;
    @Autowired
    private FormaPagamentoConverterInterface formaPagamentoConverter;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private FormaPagamentoValidateInterface formaPagamentoValidate;


    public FormaPagamento edit(FormaPagamentoEditDTO dto, Usuario usuario){
        var formaPagamento = formaPagamentoList.list(dto.id(), usuario);
        var formaPagamentoNew = formaPagamentoConverter.toFormaPagamento(dto);
        formaPagamentoValidate.validate(formaPagamentoNew);
        formaPagamento.setStatus(formaPagamentoNew.getStatus());
        formaPagamento.setNome(formaPagamentoNew.getNome());

        return formaPagamentoService.save(formaPagamento);
    }
}
