package com.gregorian.api.DTO;

import com.gregorian.api.Models.FormaPagamento.FormaPagamentoStatusEnum;

public record FormaPagamentoCreateDTO(
    String nome,
    FormaPagamentoStatusEnum status
) {
    
}
