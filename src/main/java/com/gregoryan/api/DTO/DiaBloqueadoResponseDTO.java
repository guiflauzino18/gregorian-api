package com.gregoryan.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Calendar;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaBloqueadoResponseDTO extends RepresentationModel<DiaBloqueadoResponseDTO> {

    long id;
    String nome;
    Calendar dia;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiaBloqueadoResponseDTO that = (DiaBloqueadoResponseDTO) o;
        return id == that.id && Objects.equals(nome, that.nome) && Objects.equals(dia, that.dia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nome, dia);
    }
}
