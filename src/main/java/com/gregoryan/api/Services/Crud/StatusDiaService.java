package com.gregoryan.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Repositorys.StatusDiaRepository;

import jakarta.transaction.Transactional;

@Service
public class StatusDiaService {
    @Autowired
    private StatusDiaRepository repository;

    @Transactional
    public StatusDia save(StatusDia statusDia){
        return repository.save(statusDia);
    }

    @Transactional
    public void delete (StatusDia statusDia){
        repository.delete(statusDia);
    }

    public Optional<StatusDia> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Optional<StatusDia> findById(long id){
        return repository.findById(id);
    }

    public Page<StatusDia> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<StatusDia> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    


}
