package com.gregoryan.api.Models;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_plano_empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanoEmpresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private float valor;

    private float desconto;

    @Column(nullable = false)
    private Calendar dataRegistro;

    @Column(nullable = false)
    private Calendar vigencia;

}
