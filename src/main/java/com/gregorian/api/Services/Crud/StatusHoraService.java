package com.gregorian.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.StatusHora;
import com.gregorian.api.Repositorys.StatusHoraRepository;
import jakarta.transaction.Transactional;

@Service
public class StatusHoraService {

    @Autowired
    private StatusHoraRepository repository;

    @Transactional
    public StatusHora save(StatusHora statusHora){
        return repository.save(statusHora);
    }

    @Transactional
    public void delete(StatusHora statusHora){
        repository.delete(statusHora);
    }

    public Optional<StatusHora> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public Page<StatusHora> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<StatusHora> findById(long id){
        return repository.findById(id);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Page<StatusHora> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }
    
}
