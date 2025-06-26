package com.gregoryan.api.DTO;

import java.util.Calendar;

import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario.StatusUsuario;
import org.springframework.hateoas.RepresentationModel;

public final class UsuarioResponseDTO() extends RepresentationModel<UsuarioResponseDTO> {

    long id;
    String nome;
    String sobrenome;
    Calendar nascimento;
    String telefone;
    String email;
    String login;
    String endereco;
    StatusUsuario status;
    boolean alteraNextLogon;
    UserRole role;
    Calendar dataRegistro;
    String empresaNome;

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
