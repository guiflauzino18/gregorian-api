package com.gregoryan.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.StatusAtendimento;
import com.gregoryan.api.Repositorys.StatusAtendimentoRepository;

import jakarta.transaction.Transactional;

@Service
public class StatusAtendimentoService {
    @Autowired
    private StatusAtendimentoRepository repository;

    @Transactional
    public StatusAtendimento save(StatusAtendimento statusAtendimento){
        return repository.save(statusAtendimento);
    }

    @Transactional
    public void delete(StatusAtendimento statusAtendimento){
        repository.delete(statusAtendimento);
    }

    public Optional<StatusAtendimento> findByNome(String nome){
        return repository.findByNome(nome);
    }

    boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Optional<StatusAtendimento> findById(long id){
        return repository.findById(id);
    } 

    
}
