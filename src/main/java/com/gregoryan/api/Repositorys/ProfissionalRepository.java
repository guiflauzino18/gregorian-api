package com.gregoryan.api.Repositorys;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gregoryan.api.Models.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long>{
    boolean existsByRegistro(String registro);
    Optional<Profissional> findByRegistro(String registro);
}
