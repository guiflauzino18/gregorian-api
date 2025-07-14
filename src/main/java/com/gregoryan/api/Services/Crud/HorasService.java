package com.gregoryan.api.Services.Crud;

import com.gregoryan.api.Models.Hora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Repositorys.HorasRepository;

import jakarta.transaction.Transactional;

@Service
public class HorasService {
    @Autowired
    private HorasRepository repository;

    @Transactional
    public Hora save(Hora hora){
        return repository.save(hora);
    }

    @Transactional
    public void delete (Hora hora){
        repository.delete(hora);
    }

    public Optional<Hora> findById(long id){
        return repository.findById(id);
    }

    public Page<Hora> findByStatusHora(StatusHora statusHora, Pageable pageable){
        return repository.findByStatusHora(statusHora, pageable);
    }

    public Page<Hora> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }
}
