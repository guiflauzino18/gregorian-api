package com.gregoryan.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
     UserDetails findByLogin(String login);
     Page<Usuario> findByEmpresa(Empresa empresa, Pageable pageable);
     boolean existsByLogin(String login);
}
