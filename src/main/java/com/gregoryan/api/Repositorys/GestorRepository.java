package com.gregoryan.api.Repositorys;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gregoryan.api.Models.Gestor;

public interface GestorRepository extends JpaRepository<Gestor, UUID> {

    UserDetails findByLogin(String login);
}
