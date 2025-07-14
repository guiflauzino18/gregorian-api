package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Models.Endereco;
import com.gregoryan.api.Services.Crud.EnderecoService;

@Service
public class EnderecoCreateService {
    @Autowired
    private EnderecoService enderecoService;
    public Endereco create(Endereco endereco){

        return enderecoService.save(endereco);

    }
}
