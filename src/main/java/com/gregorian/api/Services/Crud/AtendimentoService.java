package com.gregorian.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.Agendamento;
import com.gregorian.api.Models.Atendimento;
import com.gregorian.api.Models.StatusAtendimento;
import com.gregorian.api.Repositorys.AtendimentoRepository;

import jakarta.transaction.Transactional;

@Service
public class AtendimentoService {
    
    @Autowired
    private AtendimentoRepository repository;

    @Transactional
    public Atendimento save(Atendimento atendimento){
        return repository.save(atendimento);
    }

    @Transactional
    public void delete(Atendimento atendimento){
        repository.delete(atendimento);
    }

    public Page<Atendimento> findByStatusAtendimento(StatusAtendimento statusAtendimento, Pageable pageable){
        return repository.findByStatusAtendimento(statusAtendimento, pageable);
    }

    public Optional<Atendimento> findByAgendamento(Agendamento agendamento){
        return repository.findByAgendamento(agendamento);
    }

    public Optional<Atendimento> findById(long id){
        return repository.findById(id);
    }

    public Page<Atendimento> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
}
