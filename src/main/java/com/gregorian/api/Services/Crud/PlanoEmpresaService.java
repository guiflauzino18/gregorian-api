package com.gregorian.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.PlanoEmpresa;
import com.gregorian.api.Repositorys.PlanoEmpresaRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanoEmpresaService {
    
    @Autowired
    private PlanoEmpresaRepository repository;

    @Transactional
    public PlanoEmpresa save(PlanoEmpresa planoEmpresa){
        return repository.save(planoEmpresa);
    }

    @Transactional
    public void delete(PlanoEmpresa planoEmpresa){
        repository.delete(planoEmpresa);
    }

    public Optional<PlanoEmpresa> findById(long id){
        return repository.findById(id);
    }

    public boolean existsByValor(float valor){
        return repository.existsByValor(valor);
    }

    public Page<PlanoEmpresa> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<PlanoEmpresa> findByNome(String nome, Pageable pageable){
        return repository.findByNome(nome, pageable);
    }
}
