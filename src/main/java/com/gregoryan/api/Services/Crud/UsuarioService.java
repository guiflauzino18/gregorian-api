package com.gregoryan.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Repositorys.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> findAll(Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    public boolean existByLogin(String login){
        return usuarioRepository.existsByLogin(login);
    }

    public Optional<Usuario> findById(long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByLogin(String login){
        Usuario usuario = (Usuario) usuarioRepository.findByLogin(login);
        return Optional.of(usuario);
    }

    public Page<Usuario> findByEmpresa(Empresa empresa, Pageable pageable){
        return usuarioRepository.findByEmpresa(empresa, pageable);
    }
    
}
