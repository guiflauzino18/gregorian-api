package com.gregorian.api.DTO;

public record planoPacienteCadastroDTO(String nome, float valor, float desconto, int sessoes) {
    
}


// private String nome;

//     @Column(nullable = false)
//     private float valor;

//     private float desconto;

//     private int sessoes;

//     @Column(nullable = false, length = 2)
//     private int status;

//     @ManyToOne
//     @JoinColumn(name = "empresa_fk")
//     private Empresa empresa;