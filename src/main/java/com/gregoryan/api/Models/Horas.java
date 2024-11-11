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

import java.time.LocalTime;

@Entity
@Table(name = "tbl_horas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Horas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private LocalTime inicio;

    @Column(nullable = false)
    private LocalTime fim;

    @ManyToOne
    @JoinColumn(name = "status_hora_fk")
    private StatusHora statusHora;
}
