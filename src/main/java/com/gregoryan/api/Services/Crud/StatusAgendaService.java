package com.gregoryan.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Repositorys.StatusAgendaRepository;

import jakarta.transaction.Transactional;

@Service
public class StatusAgendaService {
    @Autowired
    private StatusAgendaRepository repository;

    @Transactional
    public StatusAgenda save(StatusAgenda statusAgenda){
        return repository.save(statusAgenda);
    }

    @Transactional
    public void delete(StatusAgenda statusAgenda){
        repository.delete(statusAgenda);
    }

    public Optional<StatusAgenda> findById(long id){
        return repository.findById(id);
    }

    public Optional<StatusAgenda> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Page<StatusAgenda> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
}
