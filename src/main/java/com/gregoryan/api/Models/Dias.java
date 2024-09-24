package com.gregoryan.api.Models;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_dias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dias {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany
    @JoinColumn(name = "dias_fk")
    private List<Horas> horas;

    
    private int intervaloSesssao;

    @ManyToOne
    @JoinColumn(name = "status_dia_fk")
    private StatusDia StatusDia;

}