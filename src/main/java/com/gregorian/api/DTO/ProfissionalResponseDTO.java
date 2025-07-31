package com.gregorian.api.DTO;

import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Models.Profissional.StatusProfissional;
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
public class ProfissionalResponseDTO extends RepresentationModel<ProfissionalResponseDTO> {
    long id;
    String titulo;
    String registro;
    String nome;
    String sobrenome;
    String login;
    String empresaNome;
    StatusProfissional status;
    Agenda agenda;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProfissionalResponseDTO that = (ProfissionalResponseDTO) o;
        return id == that.id && Objects.equals(titulo, that.titulo) && Objects.equals(registro, that.registro) && Objects.equals(nome, that.nome) && Objects.equals(sobrenome, that.sobrenome) && Objects.equals(login, that.login) && Objects.equals(empresaNome, that.empresaNome) && status == that.status && Objects.equals(agenda, that.agenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, titulo, registro, nome, sobrenome, login, empresaNome, status, agenda);
    }
}
