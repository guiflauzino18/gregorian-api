package com.gregorian.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Models.Endereco;
import com.gregorian.api.Services.Crud.EnderecoService;

@Service
public class EnderecoCreateService {
    @Autowired
    private EnderecoService enderecoService;
    public Endereco create(Endereco endereco){

        return enderecoService.save(endereco);

    }
}
