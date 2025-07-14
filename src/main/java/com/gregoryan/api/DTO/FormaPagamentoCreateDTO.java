package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.FormaPagamento.FormaPagamentoStatusEnum;

public record FormaPagamentoCreateDTO(
    String nome,
    FormaPagamentoStatusEnum status
) {
    
}
