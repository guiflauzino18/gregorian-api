package com.gregoryan.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Repositorys.DiasRepository;

import jakarta.transaction.Transactional;

@Service
public class DiasService {
    @Autowired
    private DiasRepository repository;

    @Transactional
    public Dias save(Dias dias){
        System.out.println("Salvou o dia");
        return repository.save(dias);
    }

    @Transactional
    public void delete(Dias dias){
        repository.delete(dias);
    }

    public Optional<Dias> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Optional<Dias> findById(Long idLong){
        return repository.findById(idLong);
    }

    public Page<Dias> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<Dias> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public Optional<Long> getAgenda(long idDia){
        return repository.getAgenda(idDia);
    }

    
}
