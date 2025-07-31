package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.FormaPagamentoEditDTO;
import com.gregorian.api.Models.FormaPagamento;
import com.gregorian.api.Services.Crud.FormaPagamentoService;
import com.gregorian.api.Interfaces.FormaPagamentoConverterInterface;
import com.gregorian.api.Interfaces.FormaPagamentoListInterface;
import com.gregorian.api.Interfaces.FormaPagamentoValidateInterface;

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
