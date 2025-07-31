package com.gregorian.api.Services.Crud;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Optional;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Faturamento;
import com.gregorian.api.Models.Paciente;
import com.gregorian.api.Repositorys.FaturamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class FaturamentoService {
    
    @Autowired
    private FaturamentoRepository repository;

    @Transactional
    public Faturamento save(Faturamento faturamento){
        return repository.save(faturamento);
    }

    @Transactional
    public void delete(Faturamento faturamento){
        repository.delete(faturamento);
    }

    public Page<Faturamento> findByData(Calendar data, Pageable pageable){
        return repository.findByData(data, pageable);
    }

    public Page<Faturamento> findByPaciente(Paciente paciente, Pageable pageable){
        return repository.findByPaciente(paciente, pageable);
    }

    public Page<Faturamento> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public Optional<Faturamento> findById(long id){
        return repository.findById(id);
    }

    public Page<Faturamento> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

}
