package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Services.Interfaces.StatusHoraValidateInterface;

@Service
public class StatusHoraValidateService implements StatusHoraValidateInterface{

    @Autowired
    private StatusHoraService statusHoraService;

    @Override
    public void jaExiste(String nome) {
        if (statusHoraService.existsByNome(nome)){
            throw new ConflictException("Já existe um status com esse nome");
        }
    }

    @Override
    public void deleteNotPermited(long id) {
        if (id == 1 || id == 2){
            throw new AcessoNegadoException("Este status não pode ser excluído");
        }
    }
    
}
