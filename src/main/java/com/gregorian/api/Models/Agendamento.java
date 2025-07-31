package com.gregorian.api.Models;

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
@Table(name = "tbl_agendamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "profissional_fk")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "paciente_fk")
    private Paciente paciente;

    @Column(nullable = false)
    private Calendar data;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;

    @Column(nullable = false)
    private Calendar dataRegistro;

    @ManyToOne
    @JoinColumn(name = "status_agendamento_fk")
    private StatusAgendamento statusAgendamento;

    @ManyToOne
    @JoinColumn(name = "dia_fk")
    private Dias dia;
}
