package com.gregorian.api.DTO;

public record EnderecoEditDTO(
    long id,
    String rua,
    String numero,
    String complemento,
    String CEP,
    String cidade,
    String bairro,
    String UF
) {
    
}
