package com.gregoryan.api.DTO;

public record FaturamentoEditDTO(
    long id,
    float valor,
    float desconto,
    float pago,
    long idPaciente,
    long idFormaPagamento
) {
    
}
