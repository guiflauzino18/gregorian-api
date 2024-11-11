package com.gregoryan.api.DTO;

public record EmpresaSaveDTO(String nome, long cnpj, String endereco, String telefone) {
    
}


// @Column(nullable = false)
// private String nome;

// @Column(nullable = false, length = 16)
// private long cnpj;

// @Column(nullable = false)
// private String endereco;

// @Column(nullable = false)
// private String telefone;

// private String responsavel;