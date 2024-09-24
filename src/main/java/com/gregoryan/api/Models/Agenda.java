package com.gregoryan.api.Models;

import jakarta.persistence.CascadeType;
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
import java.util.List;

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
 
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "agenda_fk")
    private List<Dias> dias;
}
