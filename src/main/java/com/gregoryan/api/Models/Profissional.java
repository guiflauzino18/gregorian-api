package com.gregoryan.api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_profissional")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profissional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;

    private String registro; //CRM ou afins;

    @OneToOne
    @JoinColumn(name = "usuario_fk")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "agenda_fk")
    private Agenda agenda;
}
