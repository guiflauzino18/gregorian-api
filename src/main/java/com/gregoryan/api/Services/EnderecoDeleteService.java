package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Services.Crud.EnderecoService;
import com.gregoryan.api.Services.Interfaces.EnderecoListInterface;

@Service
public class EnderecoDeleteService {
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EnderecoListInterface enderecoList;

    public void delete(long id){
        var endereco = enderecoList.list(id);

        enderecoService.delete(endereco);

    }
}
