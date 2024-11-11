package com.gregoryan.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Repositorys.PlanoPacienteRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanoPacienteService {
    
    @Autowired
    private PlanoPacienteRepository repository;

    @Transactional
    public PlanoPaciente save(PlanoPaciente PlanoPaciente){
        return repository.save(PlanoPaciente);
    }

    @Transactional
    public void delete(PlanoPaciente planoPaciente){
        repository.delete(planoPaciente);
    }

    public Page<PlanoPaciente> findByEmpresa(Empresa empresa, Pageable pageable){
        return repository.findByEmpresa(empresa, pageable);
    }

    public Page<PlanoPaciente> findByNome(String nome, Pageable pageable){
        return repository.findByNome(nome, pageable);
    }

    public Optional<PlanoPaciente> findById(long id){
        return repository.findById(id);
    }

    public Page<PlanoPaciente> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
}
