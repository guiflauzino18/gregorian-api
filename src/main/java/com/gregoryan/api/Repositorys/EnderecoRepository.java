package com.gregoryan.api.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import com.gregoryan.api.Models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
