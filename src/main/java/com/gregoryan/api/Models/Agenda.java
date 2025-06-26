package com.gregoryan.api.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_agenda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "status_agenda_fk")
    private StatusAgenda statusAgenda;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;

    @JsonIgnoreProperties("agenda")
    @OneToOne()
    @JoinColumn(name = "profissional_fk")
    private Profissional profissional;
 
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "agenda_fk")
    private List<Dias> dias;
}
