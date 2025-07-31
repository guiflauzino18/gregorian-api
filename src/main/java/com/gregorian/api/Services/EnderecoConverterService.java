package com.gregorian.api.Services;

import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.EnderecoEditDTO;
import com.gregorian.api.Models.Endereco;
import com.gregorian.api.Interfaces.EnderecoConverterInterface;

@Service
public class EnderecoConverterService implements EnderecoConverterInterface{

    @Override
    public Endereco toEndereco(EnderecoEditDTO dto) {
        return new Endereco(dto.id(), dto.rua(), dto.numero(), dto.complemento(), dto.CEP(), dto.cidade(), dto.bairro(), dto.UF());

    }
    
}