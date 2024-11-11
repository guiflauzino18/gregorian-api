package com.gregoryan.api.Services.Crud;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Models.StatusEmpresa;
import com.gregoryan.api.Repositorys.StatusEmpresaRepository;
import jakarta.transaction.Transactional;

@Service
public class StatusEmpresaService {
    
    @Autowired
    private StatusEmpresaRepository repository;

    @Transactional
    public StatusEmpresa save(StatusEmpresa statusEmpresa){
        return repository.save(statusEmpresa);
    }

    @Transactional
    public void delete (StatusEmpresa statusEmpresa){
        repository.delete(statusEmpresa);
    }

    public Page<StatusEmpresa> findByNome(String nome, Pageable pageable){
        return repository.findByNome(nome, pageable);
    }

    public Optional<StatusEmpresa> findById(long id){
        return repository.findById(id);
    }

    public Page<StatusEmpresa> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }
}
