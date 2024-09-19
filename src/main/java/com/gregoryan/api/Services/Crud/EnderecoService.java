package com.gregoryan.api.Services.Crud;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Models.Endereco;
import com.gregoryan.api.Repositorys.EnderecoRepository;

import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public ResponseEntity<Endereco> save(Endereco endereco){
        return new ResponseEntity<Endereco>(enderecoRepository.save(endereco), HttpStatus.CREATED);
    }

    @Transactional
    public void delete(Endereco endereco){
        enderecoRepository.delete(endereco);
    }

    public Page<Endereco> findAll(Pageable pageable){
        return enderecoRepository.findAll(pageable);
    }

    public Optional<Endereco> findById(long id){
        return enderecoRepository.findById(id);
    }
}
