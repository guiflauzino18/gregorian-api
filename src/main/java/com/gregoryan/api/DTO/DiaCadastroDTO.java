package com.gregoryan.api.DTO;

public record DiaCadastroDTO(long id, String nome, long intervaloSessaoInMinutes, long duracaoSessaoInMinutes, String inicio, String fim) {
    
}
