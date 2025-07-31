package com.gregorian.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Endereco;
import com.gregorian.api.Services.Crud.EnderecoService;
import com.gregorian.api.Interfaces.EnderecoListInterface;

@Service
public class EnderecoListService implements EnderecoListInterface{

    @Autowired
    private EnderecoService enderecoService;

    @Override
    public Endereco list(long id) {
        var endereco = enderecoService.findById(id).orElseThrow(() -> new EntityDontExistException("Endereço não encontrado"));

        return endereco;
    }
    
}
