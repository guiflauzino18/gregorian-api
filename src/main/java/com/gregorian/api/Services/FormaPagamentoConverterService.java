package com.gregorian.api.Services;

import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.FormaPagamentoCreateDTO;
import com.gregorian.api.DTO.FormaPagamentoEditDTO;
import com.gregorian.api.DTO.FormaPagamentoResponseDTO;
import com.gregorian.api.Models.FormaPagamento;
import com.gregorian.api.Interfaces.FormaPagamentoConverterInterface;

@Service
public class FormaPagamentoConverterService implements FormaPagamentoConverterInterface{

    @Override
    public FormaPagamento toFormaPagamento(FormaPagamentoCreateDTO dto) {
        var formaPagamento = new FormaPagamento();
        formaPagamento.setNome(dto.nome());
        formaPagamento.setStatus(dto.status());

        return formaPagamento;
    }

    @Override
    public FormaPagamento toFormaPagamento(FormaPagamentoEditDTO dto) {
        var formaPagamento = new FormaPagamento();
        formaPagamento.setNome(dto.nome());
        formaPagamento.setStatus(dto.status());

        return formaPagamento;
    }

    @Override
    public FormaPagamentoResponseDTO toResponseDTO(FormaPagamento formaPagamento) {

        FormaPagamentoResponseDTO dto = new FormaPagamentoResponseDTO(
                formaPagamento.getId(), 
                formaPagamento.getNome(),
                formaPagamento.getStatus()    
            );

        return dto;
    }
    
}
