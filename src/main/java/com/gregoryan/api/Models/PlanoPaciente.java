package com.gregoryan.api.Models;

import java.util.Calendar;

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
@Table(name = "tbl_plano_paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanoPaciente {

    public static final int PLANO_STATUS_ATIVO = 1;
    public static final int PLANO_STATUS_INATIVO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private float valor;

    private float desconto;

    private int sessoes;

    @Column(nullable = false, length = 2)
    private int status;

    @Column(nullable = false)
    private Calendar dataRegistro;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;

    public float getTotal(){
        return valor - desconto;
    }
    
}
