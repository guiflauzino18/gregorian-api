package com.gregoryan.api.Repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gregoryan.api.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
    
     UserDetails findByLogin(String login);
}
