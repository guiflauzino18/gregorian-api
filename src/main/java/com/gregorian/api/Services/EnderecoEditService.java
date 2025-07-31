package com.gregorian.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.EnderecoEditDTO;
import com.gregorian.api.Models.Endereco;
import com.gregorian.api.Services.Crud.EnderecoService;
import com.gregorian.api.Interfaces.EnderecoConverterInterface;
import com.gregorian.api.Interfaces.EnderecoListInterface;

@Service
public class EnderecoEditService {

    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EnderecoConverterInterface enderecoConverter;
    @Autowired
    private EnderecoListInterface enderecoList;

    public Endereco edit(EnderecoEditDTO dto){
        var endereco = enderecoList.list(dto.id()); //EntityDontExistException
        var enderecoEdit = enderecoConverter.toEndereco(dto);

        endereco.setRua(enderecoEdit.getRua());
        endereco.setNumero(enderecoEdit.getNumero());
        endereco.setComplemento(enderecoEdit.getComplemento());
        endereco.setCEP(enderecoEdit.getCEP());
        endereco.setCidade(enderecoEdit.getCidade());
        endereco.setBairro(enderecoEdit.getBairro());
        endereco.setUF(enderecoEdit.getUF());

        enderecoService.save(endereco);

        return endereco;
    }
}