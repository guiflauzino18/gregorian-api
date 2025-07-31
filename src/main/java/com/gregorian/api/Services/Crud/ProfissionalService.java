package com.gregorian.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Repositorys.ProfissionalRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfissionalService {
    
    @Autowired
    private ProfissionalRepository repository;

    @Transactional
    public Profissional save(Profissional profissional){
        return repository.save(profissional);
    }

    @Transactional
    public void delete(Profissional profissional){
        repository.delete(profissional);
    }

    public Optional<Profissional> findById(long id){
        return repository.findById(id);
    }

    public Page<Profissional> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public boolean existsByRegistro(String Registro){
        return repository.existsByRegistro(Registro);
    }

    public Page<Profissional> findByEmpresa(long id, Pageable pageable){
        return repository.findByEmpresa(id, pageable);
    }

    public Optional<Profissional> findByUsuario(Usuario Usuario){
        return repository.findByUsuario(Usuario);
    }
    
}
