package com.gregoryan.api.Repositorys;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gregoryan.api.Models.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long>{
    boolean existsByRegistro(String registro);
    Optional<Profissional> findByRegistro(String registro);

    @Query(value = " select p.*, u.nome, u.sobrenome, u.email, u.endereco, u.nascimento, u.telefone from tbl_profissional p left join tbl_usuario u on (p.usuario_fk = u.id) where u.empresa_fk = :idEmpresa", nativeQuery = true)
    Page<Profissional> findByEmpresa(long idEmpresa, Pageable pageable);
}
