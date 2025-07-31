package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.FaturamentoEditDTO;
import com.gregorian.api.Models.Faturamento;
import com.gregorian.api.Services.Crud.FaturamentoService;
import com.gregorian.api.Interfaces.FaturamentoConverterInterface;
import com.gregorian.api.Interfaces.FaturamentoListInterface;

@Service
public class FaturamentoEditService {
    @Autowired
    private FaturamentoListInterface faturamentoList;
    @Autowired
    private FaturamentoConverterInterface faturamentoConverter;
    @Autowired
    private FaturamentoService faturamentoService;

    public Faturamento edit(FaturamentoEditDTO dto, Usuario usuario){

        var faturamento = faturamentoList.list(dto.id(), usuario);
        var faturamentoNew = faturamentoConverter.toFaturamento(dto, usuario);

        faturamento.setValor(faturamentoNew.getValor());
        faturamento.setDesconto(faturamentoNew.getDesconto());
        faturamento.setPago(faturamentoNew.getPago());
        faturamento.setPaciente(faturamentoNew.getPaciente());
        faturamento.setFormaPagamento(faturamentoNew.getFormaPagamento());
        return faturamentoService.save(faturamento);
    }

}