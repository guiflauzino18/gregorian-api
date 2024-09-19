package com.gregoryan.api.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gregoryan.api.Models.Gestor;

public interface GestorRepository extends JpaRepository<Gestor, Long> {

    UserDetails findByLogin(String login);
}
