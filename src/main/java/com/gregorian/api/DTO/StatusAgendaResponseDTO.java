package com.gregorian.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusAgendaResponseDTO extends RepresentationModel<StatusAgendaResponseDTO> {
    long id;
    String nome;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StatusAgendaResponseDTO that = (StatusAgendaResponseDTO) o;
        return id == that.id && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nome);
    }
}
