package com.gregoryan.api.Services.Crud;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.FaturamentoEmpresa;
import com.gregoryan.api.Repositorys.FaturamentoEmpresaRepository;

import jakarta.transaction.Transactional;

@Service
public class FaturamentoEmpresaService {
    
    @Autowired
    private FaturamentoEmpresaRepository repository;

    @Transactional
    public FaturamentoEmpresa save(FaturamentoEmpresa faturamento){
        return repository.save(faturamento);
    }

    @Transactional
    public void delete(FaturamentoEmpresa faturamento){
        repository.delete(faturamento);
    }

    public Optional<FaturamentoEmpresa> findById(long id){
        return repository.findById(id);
    }

    public Page<FaturamentoEmpresa> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Page<FaturamentoEmpresa> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public Page<FaturamentoEmpresa> findByData(Calendar data, Pageable pageable){
        return repository.findByData(data, pageable);
    }

    


}
