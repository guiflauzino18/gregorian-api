package com.gregorian.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  DiaResponseDTO extends RepresentationModel<DiaResponseDTO> {

    long id;
    String nome;
    long intervaloSessaoInMinutes;
    long duracaoSessaoInMinutes;
    StatusDiaResponseDTO status;
    LocalTime inicio;
    LocalTime fim;
}
