package com.gregoryan.api.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gregoryan.api.Models.StatusHora;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalTime;

@Entity
@Table(name = "tbl_horas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoraResposeDTO extends RepresentationModel<HoraResposeDTO> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private LocalTime inicio;

    @Column(nullable = false)
    private LocalTime fim;

    @ManyToOne
    @JoinColumn(name = "status_hora_fk")
    @JsonIgnoreProperties("empresa")
    private StatusHora statusHora;
}
