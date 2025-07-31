package com.gregorian.api.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_forma_pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 2)
    private FormaPagamentoStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;

    
    public enum FormaPagamentoStatusEnum {
        ATIVO(1), INATIVO(0);
        private final int status;

        private FormaPagamentoStatusEnum (int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }

    }

}
