package com.gregorian.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Paciente;
import com.gregorian.api.Repositorys.PacienteRepository;
import jakarta.transaction.Transactional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public Paciente save(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void delete(Paciente paciente){
        pacienteRepository.delete(paciente);
    }

    public Page<Paciente> findByNome(String nome, Pageable pageable){
        return pacienteRepository.findByNome(nome, pageable);
    }

    public Page<Paciente> findBySobrenome(String sobrenome, Pageable pageable){
        return pacienteRepository.findByNome(sobrenome, pageable);
    }

    public Optional<Paciente> findByCpf(long cpf){
        return pacienteRepository.findByCpf(cpf);
    }

    public Page<Paciente> findAll(Pageable pageable){
        return pacienteRepository.findAll(pageable);
    }

    public Optional<Paciente> findById(long id){
        return pacienteRepository.findById(id);
    }

    public Page<Paciente> findByTelefone(long telefone, Pageable pageable){
        return pacienteRepository.findByTelefone(telefone, pageable);
    }

    public boolean existsByCpf(long cpf){
        return pacienteRepository.existsByCpf(cpf);
    }

    public Page<Paciente> findByEmpresa(Empresa empresa, Pageable pageable){
        return pacienteRepository.findByEmpresa(empresa, pageable);
    }
    
}
