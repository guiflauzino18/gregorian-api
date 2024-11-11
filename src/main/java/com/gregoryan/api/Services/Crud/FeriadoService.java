package com.gregoryan.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Repositorys.FeriadoRepository;

import jakarta.transaction.Transactional;

@Service
public class FeriadoService {
    
    @Autowired
    private FeriadoRepository repository;

    @Transactional
    public Feriado save(Feriado feriado){
        return repository.save(feriado);
    }

    @Transactional
    public void delete(Feriado feriado){
        repository.delete(feriado);
    }

    public Page<Feriado> findByNome(String nome, Pageable pageable){
        return repository.findByNome(nome, pageable);
    }

    public Optional<Feriado> findById(long id){
        return repository.findById(id);
    }

    public Page<Feriado> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<Feriado> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    
}
