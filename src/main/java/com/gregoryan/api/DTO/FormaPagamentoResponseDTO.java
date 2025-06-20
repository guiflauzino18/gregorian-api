package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.FormaPagamento.FormaPagamentoStatusEnum;

public record FormaPagamentoResponseDTO(
    long id,
    String nome,
    FormaPagamentoStatusEnum status
) {}
