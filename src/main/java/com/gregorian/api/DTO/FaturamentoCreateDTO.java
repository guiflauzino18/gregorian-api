package com.gregorian.api.DTO;

public record FaturamentoCreateDTO(
    float valor,
    float desconto,
    float pago,
    long idPaciente,
    long idFormaPagamento
    ) {
    
}
