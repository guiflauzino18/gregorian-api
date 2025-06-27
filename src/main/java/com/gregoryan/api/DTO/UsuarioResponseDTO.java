package com.gregoryan.api.DTO;

import java.util.Calendar;

import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario.StatusUsuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {


    private long id;
    private String nome;
    private String sobrenome;
    private Calendar nascimento;
    private String telefone;
    private String email;
    private String login;
    private String endereco;
    private StatusUsuario status;
    private boolean alteraNextLogon;
    private UserRole role;
    private Calendar dataRegistro;
    private String empresaNome;

    public UsuarioResponseDTO(long id, String nome, String sobrenome, Calendar nascimento, String telefone, String email, String login, String endereco, StatusUsuario status, boolean alteraNextLogon, UserRole role, Calendar dataRegistro, String empresaNome) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.email = email;
        this.login = login;
        this.endereco = endereco;
        this.status = status;
        this.alteraNextLogon = alteraNextLogon;
        this.role = role;
        this.dataRegistro = dataRegistro;
        this.empresaNome = empresaNome;
    }

    public UsuarioResponseDTO(){};


    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "UsuarioResponseDTO[]";
    }

}
