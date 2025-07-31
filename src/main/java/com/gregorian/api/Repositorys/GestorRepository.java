package com.gregorian.api.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.gregorian.api.Models.Gestor;

public interface GestorRepository extends JpaRepository<Gestor, Long> {

    UserDetails findByLogin(String login);
}
