package com.gregorian.api.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
     UserDetails findByLogin(String login);
     Page<Usuario> findByEmpresa(Empresa empresa, Pageable pageable);
     boolean existsByLogin(String login);

     @Query(value = """
             SELECT * FROM tbl_usuario
             WHERE id = %:input%
               OR nome like %:input%
               OR sobrenome like %:input%
               OR nascimento like %:input%
               OR telefone like %:input%
               OR endereco like %:input%
               OR email like %:input%
               OR login like %:input%
               AND empresa_fk = :empresa
             """
             ,nativeQuery = true)
     Page<Usuario> search(long empresa, String input, Pageable pageable);
}
