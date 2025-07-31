package com.gregorian.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Repositorys.EmpresaRepository;

import jakarta.transaction.Transactional;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public Empresa save(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public Page<Empresa> findAll(Pageable pageable){
        return empresaRepository.findAll(pageable);
    }

    public Optional<Empresa> findById(long id){
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> findByCnpj(long cnpj){
        return empresaRepository.findByCnpj(cnpj);
    }

    @Transactional
    public void delete(Empresa empresa){
        empresaRepository.delete(empresa);
    }

    public boolean existsByCnpj(long cnpj){
        return empresaRepository.existsByCnpj(cnpj);
    }

}
