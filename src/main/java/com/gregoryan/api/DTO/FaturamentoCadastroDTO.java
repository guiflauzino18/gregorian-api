package com.gregoryan.api.DTO;

public record FaturamentoCadastroDTO(
    float valor,
    float desconto,
    float pago,
    long idPaciente,
    long idFormaPagamento
    ) {
    
}
