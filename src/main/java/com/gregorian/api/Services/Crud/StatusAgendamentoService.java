package com.gregorian.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregorian.api.Models.StatusAgendamento;
import com.gregorian.api.Repositorys.StatusAgendamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class StatusAgendamentoService {
    @Autowired
    private StatusAgendamentoRepository repository;

    @Transactional
    public StatusAgendamento save(StatusAgendamento statusAgendamento){
        return repository.save(statusAgendamento);
    }

    @Transactional
    public void delete(StatusAgendamento statusAgendamento){
        repository.delete(statusAgendamento);
    }

    public Optional<StatusAgendamento> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Optional<StatusAgendamento> findById(long id){
        return repository.findById(id);
    }

    public Page<StatusAgendamento> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }


}
