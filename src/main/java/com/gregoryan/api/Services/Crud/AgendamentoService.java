package com.gregoryan.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgendamento;
import com.gregoryan.api.Repositorys.AgendamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository repository;

    @Transactional
    public Agendamento save(Agendamento agendamento){
        return repository.save(agendamento);
    }

    @Transactional
    public void delete(Agendamento agendamento){
        repository.delete(agendamento);
    }

    public Page<Agendamento> findByProfissional(Profissional profissional, Pageable pageable){
        return repository.findByProfissional(profissional, pageable);
    }

    public Page<Agendamento> findByPaciente(Paciente paciente, Pageable pageable){
        return repository.findByPaciente(paciente, pageable);
    }

    public Page<Agendamento> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public Page<Agendamento> findByStatusAgendamento(StatusAgendamento statusAgendamento, Pageable pageable){
        return repository.findByStatusAgendamento(statusAgendamento, pageable);
    }

    public Page<Agendamento> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<Agendamento> findById(long id){
        return repository.findById(id);
    }
}
