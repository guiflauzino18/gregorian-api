package com.gregorian.api.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import com.gregorian.api.Models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
