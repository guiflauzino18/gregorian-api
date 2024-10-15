package com.gregoryan.api.Models;

import jakarta.persistence.Column;
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
import java.time.LocalTime;


@Entity
@Table(name = "tbl_atendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atendimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "agendamento_fk")
    private Agendamento agendamento;

    @Column(columnDefinition = "TEXT")
    private String laudo;

    @OneToOne
    @JoinColumn(name = "status_atendimento_fk")
    private StatusAtendimento StatusAtendimento;

    private LocalTime horainicio;

    private LocalTime horaatual;

    private LocalTime horafim;


    public long tempoSessaoRestanteInminutes(){
        long horaInicio = horainicio.toSecondOfDay();
        long horaAtual = horaatual.toSecondOfDay();

        long tempoDecorrido = horaAtual - horaInicio;

        return agendamento.getDia().getDuracaoSessaoInMinutes() - tempoDecorrido*60;
    }

    public LocalTime horaPrevistaparaFim(){
        return horainicio.plusMinutes(agendamento.getDia().getDuracaoSessaoInMinutes());
    }
}
