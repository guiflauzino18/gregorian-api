package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Endereco;
import com.gregoryan.api.Services.Crud.EnderecoService;
import com.gregoryan.api.Interfaces.EnderecoListInterface;

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
