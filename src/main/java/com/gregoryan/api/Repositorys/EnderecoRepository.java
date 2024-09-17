package com.gregoryan.api.Repositorys;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gregoryan.api.Models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    
}
