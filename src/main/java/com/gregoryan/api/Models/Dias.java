package com.gregoryan.api.Models;

import java.util.ArrayList;
import java.util.List;

import com.gregoryan.api.Services.Crud.HorasService;

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

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "tbl_dias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dias implements Serializable{

    // @Autowired
    // private StatusHoraService statusHoraService;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dia_fk")
    private List<Horas> horas;

    private long intervaloSesssaoInMinutes;

    private long duracaoSessaoInMinutes;

    @ManyToOne
    @JoinColumn(name = "status_dia_fk")
    private StatusDia StatusDia;

    @Column(nullable = false)
    private LocalTime inicio;

    @Column(nullable = false)
    private LocalTime fim;

    public void createHoras(StatusHora statusHora, HorasService horasService){

        
        for (Horas horas2 : horas) {
            horasService.delete(horas2);
        }
        
        LocalTime incremento = inicio;
        List<Horas> horaP = new ArrayList<Horas>();

        while (incremento.isBefore(fim)) {
            Horas hora = new Horas();
            
            hora.setInicio(incremento);
            hora.setFim(incremento.plusMinutes(duracaoSessaoInMinutes));
            hora.setStatusHora(statusHora);
            horaP.add(hora);

            incremento = incremento.plusMinutes(duracaoSessaoInMinutes).plusMinutes(intervaloSesssaoInMinutes);
            
        }

        this.setHoras(horaP);
    }

}
