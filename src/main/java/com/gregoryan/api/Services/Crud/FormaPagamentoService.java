package com.gregoryan.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Repositorys.FormaPagamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class FormaPagamentoService {
    
    @Autowired
    private FormaPagamentoRepository repository;

    @Transactional
    public FormaPagamento save(FormaPagamento formaPagamento){
        return repository.save(formaPagamento);
    }

    @Transactional
    public void delete (FormaPagamento formaPagamento){
        repository.delete(formaPagamento);
    }

    public Optional<FormaPagamento> findByNome(String nome){
        return repository.findByNome(nome);
    }

    public Page<FormaPagamento> findByStatus(int status, Pageable pageable){
        return repository.findByStatus(status,pageable);
    }

    public Page<FormaPagamento> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }

    public Optional<FormaPagamento> findById(long id){
        return repository.findById(id);
    }

    public Page<FormaPagamento> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }


}
