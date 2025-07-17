package com.gregoryan.api.Services;

import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.EnderecoEditDTO;
import com.gregoryan.api.Models.Endereco;
import com.gregoryan.api.Interfaces.EnderecoConverterInterface;

@Service
public class EnderecoConverterService implements EnderecoConverterInterface{

    @Override
    public Endereco toEndereco(EnderecoEditDTO dto) {
        return new Endereco(dto.id(), dto.rua(), dto.numero(), dto.complemento(), dto.CEP(), dto.cidade(), dto.bairro(), dto.UF());

    }
    
}