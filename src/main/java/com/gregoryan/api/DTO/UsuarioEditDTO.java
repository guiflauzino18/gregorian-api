package com.gregoryan.api.DTO;

import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario.StatusUsuario;

public record UsuarioEditDTO(long id,String nome, String sobrenome, 
                String nascimento, String telefone, String email,
                String endereco, UserRole role, StatusUsuario status, boolean alteraNextLogon) {}
