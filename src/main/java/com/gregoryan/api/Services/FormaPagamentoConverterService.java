package com.gregoryan.api.Services;

import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.FormaPagamentoCreateDTO;
import com.gregoryan.api.DTO.FormaPagamentoEditDTO;
import com.gregoryan.api.DTO.FormaPagamentoResponseDTO;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Interfaces.FormaPagamentoConverterInterface;

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
