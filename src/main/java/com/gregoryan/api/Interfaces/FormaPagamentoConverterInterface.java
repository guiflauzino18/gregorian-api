package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.FormaPagamentoCreateDTO;
import com.gregoryan.api.DTO.FormaPagamentoEditDTO;
import com.gregoryan.api.DTO.FormaPagamentoResponseDTO;
import com.gregoryan.api.Models.FormaPagamento;

public interface FormaPagamentoConverterInterface {
    public FormaPagamento toFormaPagamento(FormaPagamentoCreateDTO dto);
    public FormaPagamento toFormaPagamento(FormaPagamentoEditDTO dto);
    public FormaPagamentoResponseDTO toResponseDTO(FormaPagamento formaPagamento);
}
