package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.FormaPagamentoCreateDTO;
import com.gregorian.api.DTO.FormaPagamentoEditDTO;
import com.gregorian.api.DTO.FormaPagamentoResponseDTO;
import com.gregorian.api.Models.FormaPagamento;

public interface FormaPagamentoConverterInterface {
    public FormaPagamento toFormaPagamento(FormaPagamentoCreateDTO dto);
    public FormaPagamento toFormaPagamento(FormaPagamentoEditDTO dto);
    public FormaPagamentoResponseDTO toResponseDTO(FormaPagamento formaPagamento);
}
