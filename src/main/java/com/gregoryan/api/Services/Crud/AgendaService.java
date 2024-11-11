package com.gregoryan.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Repositorys.AgendaRepository;

import jakarta.transaction.Transactional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository repository;

    @Transactional
    public Agenda save(Agenda agenda){
        return repository.save(agenda);
    }

    @Transactional
    public void delete(Agenda agenda){
        repository.delete(agenda);
    }

    public Page<Agenda> findByStatus(StatusAgenda statusAgenda, Pageable pageable){
        return repository.findByStatusAgenda(statusAgenda, pageable);
    }

    public Page<Agenda> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }
 
    public Optional<Agenda> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Optional<Agenda> findById(long id){
        return repository.findById(id);
    }

    public Page<Agenda> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    
}
