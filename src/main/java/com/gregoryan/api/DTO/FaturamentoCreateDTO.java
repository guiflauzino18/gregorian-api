package com.gregoryan.api.DTO;

public record FaturamentoCreateDTO(
    float valor,
    float desconto,
    float pago,
    long idPaciente,
    long idFormaPagamento
    ) {
    
}
