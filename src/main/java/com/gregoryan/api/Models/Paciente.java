package com.gregoryan.api.Models;

import java.util.Calendar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_paciente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    private String login;
    private String senha;

    @Column(nullable = false, length = 16)
    private long telefone;

    private String email;

    @Column(nullable = false, length = 16)
    private long cpf;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private Calendar dataRegistro;

    @ManyToOne
    @JoinColumn(name = "plano_paciente_fk")
    private PlanoPaciente planoPaciente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_fk")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;
}
