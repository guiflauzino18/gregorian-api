package com.gregoryan.api.Models;

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
import java.util.Calendar;

@Entity
@Table(name = "tbl_faturamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Faturamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false)
    private float desconto;

    @Column(nullable = false)
    private float pago;

    @ManyToOne
    @JoinColumn(name = "paciente_fk")
    private Paciente paciente;

    @Column(nullable = false)
    private Calendar data;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_fk")
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name =  "empresa_fk")
    private Empresa empresa;


    private StatusFaturamento status;

    public enum StatusFaturamento {
        GERADO(1), CANCELADO(2), FATURADO(3);

        private final int status;

        private StatusFaturamento(int valor){
            status = valor;
        }

        public int getStatus(){
            return status;
        }

        
    }


    public float getDebito(){
        return getTotal() - pago;
    }

    public float getTotal(){
        return valor - desconto;
    }

    public boolean idDebit(){
        if (pago >= getTotal()) return true; else return false;
    }


}
