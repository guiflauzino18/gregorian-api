package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEditDTO{

    private long id;
    private String nome;
    private String sobrenome;
    private String nascimento;
    private String telefone;
    private String email;
    private String endereco;
    private UserRole role;
    private StatusUsuario status;
    private boolean alteraNextLogon;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEditDTO that = (UsuarioEditDTO) o;
        return id == that.id && alteraNextLogon == that.alteraNextLogon && Objects.equals(nome, that.nome) && Objects.equals(sobrenome, that.sobrenome) && Objects.equals(nascimento, that.nascimento) && Objects.equals(telefone, that.telefone) && Objects.equals(email, that.email) && Objects.equals(endereco, that.endereco) && role == that.role && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, nascimento, telefone, email, endereco, role, status, alteraNextLogon);
    }
}

