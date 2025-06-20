package com.gregoryan.api.Services.Crud;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Repositorys.DiaBloqueadoRepository;

import jakarta.transaction.Transactional;

@Service
public class DiaBloqueadoService {
    
    @Autowired
    private DiaBloqueadoRepository repository;

    @Transactional
    public DiaBloqueado save(DiaBloqueado diaBloqueado){
        return repository.save(diaBloqueado);
    }

    @Transactional
    public void delete(DiaBloqueado diaBloqueado){
        repository.delete(diaBloqueado);
    }

    public Page<DiaBloqueado> findByNome(String nome, Pageable pageable){
        return repository.findByNome(nome,pageable);
    }

    public Optional<DiaBloqueado> findById(long id){
        return repository.findById(id);
    }

    public Page<DiaBloqueado> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<DiaBloqueado> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public Optional<DiaBloqueado> findByDia(Calendar dia){
        return repository.findByDia(dia);
    }
}
