package com.gregoryan.api.Services.Crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.gregoryan.api.Models.Horas;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Repositorys.HorasRepository;

import jakarta.transaction.Transactional;

@Service
public class HorasService {
    @Autowired
    private HorasRepository repository;

    @Transactional
    public Horas save(Horas horas){
        return repository.save(horas);
    }

    @Transactional
    public void delete (Horas horas){
        repository.delete(horas);
    }

    public Optional<Horas> findById(long id){
        return repository.findById(id);
    }

    public Page<Horas> findByStatusHora(StatusHora statusHora, Pageable pageable){
        return repository.findByStatusHora(statusHora, pageable);
    }

    public Page<Horas> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }
}
