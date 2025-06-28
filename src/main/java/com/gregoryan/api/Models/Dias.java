package com.gregoryan.api.Models;

import java.util.ArrayList;
import java.util.List;

import com.gregoryan.api.Services.Crud.HorasService;

import jakarta.persistence.*;
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dia_fk")
    private List<Hora> horas;

    private long intervaloSessaoInMinutes;

    private long duracaoSessaoInMinutes;

    @ManyToOne
    @JoinColumn(name = "status_dia_fk")
    private StatusDia StatusDia;

    @Column(nullable = false)
    private LocalTime inicio;

    @Column(nullable = false)
    private LocalTime fim;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;

    public void createHoras(StatusHora statusHora, HorasService horasService){

        if (this.getHoras() != null) {
            this.getHoras().forEach(horasService::delete);
            this.getHoras().clear();
        }
        
        LocalTime incremento = inicio;
        List<Hora> horaP = new ArrayList<>();

        while (incremento.isBefore(fim)) {
            Hora hora = new Hora();
            
            hora.setInicio(incremento);
            hora.setFim(incremento.plusMinutes(duracaoSessaoInMinutes));
            hora.setStatusHora(statusHora);
            horaP.add(hora);

            incremento = incremento.plusMinutes(duracaoSessaoInMinutes).plusMinutes(intervaloSessaoInMinutes);
        }

        this.setHoras(horaP);
    }

    public boolean recriarHora(Dias diaAtual, Dias diaNovo){
        
        boolean recriarHora = false;
        if (diaAtual.getFim().equals(diaNovo.getFim())|| 
            diaAtual.getInicio().equals(diaNovo.getInicio())|| 
            diaAtual.getDuracaoSessaoInMinutes() != diaNovo.getDuracaoSessaoInMinutes() ||
            diaAtual.getIntervaloSessaoInMinutes() != diaNovo.getIntervaloSessaoInMinutes()){
            return  recriarHora = true;
        }
        // if (diaAtual.getDuracaoSessaoInMinutes() != diaNovo.getDuracaoSessaoInMinutes() ||
        //     diaAtual.getIntervaloSessaoInMinutes() != diaNovo.getIntervaloSessaoInMinutes() ||
        //     diaAtual.getInicio() != diaNovo.getInicio() ||
        //     diaAtual.getFim() != diaNovo.getFim() ||
        //     diaAtual.getFim().
        //     ){
        //        return  recriarHora = true;
        //     }

        return recriarHora;
    }
}
