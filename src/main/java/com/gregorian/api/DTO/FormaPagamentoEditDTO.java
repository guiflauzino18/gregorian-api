package com.gregorian.api.DTO;

import com.gregorian.api.Models.FormaPagamento.FormaPagamentoStatusEnum;

public record FormaPagamentoEditDTO(
    long id,
    String nome,
    FormaPagamentoStatusEnum status
) {
    
}
